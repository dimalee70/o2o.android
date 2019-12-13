package kz.dragau.larek.ui.fragment.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.map.LocationMapView
import kz.dragau.larek.presentation.presenter.map.LocationMapPresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ferfalk.simplesearchview.SimpleSearchView
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.maps.android.PolyUtil
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import kotlinx.android.synthetic.main.activity_store.*
import kotlinx.android.synthetic.main.fragment_location_map.*
import kz.dragau.larek.App
import kz.dragau.larek.Constants
import kz.dragau.larek.api.response.SalesOutletResponse
import kz.dragau.larek.databinding.FragmentLocationMapBinding
import kz.dragau.larek.models.objects.Points
import kz.dragau.larek.models.objects.SalesOutletResult
import kz.dragau.larek.presentation.presenter.map.SaleSelector
import kz.dragau.larek.ui.adapters.SalesClusterRenderer
import kz.dragau.larek.ui.fragment.BaseMvpFragment
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.io.IOException
import java.util.*
import javax.inject.Inject

class LocationMapFragment : BaseMvpFragment(), LocationMapView,
    ClusterManager.OnClusterClickListener<SalesOutletResult>,
    ClusterManager.OnClusterItemClickListener<SalesOutletResult>,
    SimpleSearchView.OnQueryTextListener{



    private val LOCATION_REQUEST = 665
    private var mMap: GoogleMap? = null
    private var selectedPoint: LatLng? = null
    private var gps: GPS? = null
    private var searchMenu: Menu? = null
    private var item_search: MenuItem? = null
    private val lifecycleRegistry = LifecycleRegistry(this)
    private val po: PolygonOptions = PolygonOptions()
    private var clusterManager: MyClusterManager<SalesOutletResult>? = null
    private var salesOutletResponse: SalesOutletResponse? = null

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var mLocationMapPresenter: LocationMapPresenter

    @Inject
    lateinit var saleSelector: SaleSelector

    lateinit var binding: FragmentLocationMapBinding

    @ProvidePresenter
    fun providePresenter(): LocationMapPresenter
    {
        return LocationMapPresenter(router, saleSelector)
    }

    companion object {
        const val TAG = "LocationMapFragment"

        fun newInstance(): LocationMapFragment {
            val fragment: LocationMapFragment = LocationMapFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)

        mLocationMapPresenter.attachLifecycle(lifecycleRegistry)

//        val observer = Observer<SalesOutletResponse> { responce -> showSalesOutlet(responce)}

        mLocationMapPresenter.observeForSalesOutletResponse()
            .observe(this, Observer      {
                    response -> response.let {
                showSalesOutlet(response)
            }
            })

        mLocationMapPresenter.observeForSalesOutletResponseBoundary()
            .observe(this, Observer{
                response -> response.let{
                showSalesOutlet(response)
            }
            })

        mLocationMapPresenter.observeForCancellSearchButton()
            .observe(this, Observer {
                response -> response.let { if (response){
                clusteringMap()
                binding.arrowBack.visibility = View.GONE
            }
            else{
                mMap!!.clear()
                binding.arrowBack.visibility = View.VISIBLE
            } }
            })

        mLocationMapPresenter.obseverForSubmitButton()
            .observe(this, Observer {
                response -> response.let { if (response) binding.btnProceed.visibility = View.VISIBLE
                    else binding.btnProceed.visibility = View.GONE}
            })
        activity!!.toolbarCl.visibility = View.VISIBLE
        activity!!.searchView.setOnQueryTextListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_location_map, container , false)
        val frView : View  = binding.flMain
        binding.presenter = mLocationMapPresenter


         Places.initialize(App.appComponent.context(), "AIzaSyDdatI2oDODoOBHU_Hxa8hdTPAm012cNUY")
         Places.createClient(App.appComponent.context())

        return frView
    }

    override fun onResume() {
        super.onResume()
        if (mMap == null) {
            val f = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
            f.getMapAsync { googleMap ->
                mMap = googleMap
                mLocationMapPresenter.setUpMap()
            }

        }
    }

    override fun showLoading() {
        btnProceed.visibility = View.GONE
        txtMessage.text = null
        txtAddress.text = null
        clLoading.visibility = View.VISIBLE
        clLoading.visibility = View.GONE

        btnProceed.isEnabled = false
    }

    override fun hideLoading() {
        btnProceed.visibility = View.VISIBLE
        clLoading.visibility = View.GONE
    }

    private fun showError(message: String) {
        try {
            hideLoading()
            txtMessage.text = message
            txtMessage.setTextColor(ContextCompat.getColor(context!!, android.R.color.holo_red_dark))
            btnProceed.isEnabled = false
        } catch (e: Exception) {
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        activity!!.toolbarCl.visibility = View.GONE
        mLocationMapPresenter.detachLifecycle(lifecycleRegistry )
    }


    override fun changeMapType(mapType: Int) {
        mMap?.mapType = mapType
    }

    private fun showSalesOutlet(salesOutletResponse: SalesOutletResponse?) {
        mMap?.clear()
        if(salesOutletResponse!!.resultObject != null && salesOutletResponse.resultObject!!.size > 0) {
            this.salesOutletResponse = salesOutletResponse

            mLocationMapPresenter.readItems()

            mMap!!.animateCamera(CameraUpdateFactory.zoomTo(mMap!!.cameraPosition.zoom + 0.0005f), 200, null)
        }else{
            Toast.makeText(context!!, "Ничего не найдено", Toast.LENGTH_SHORT).show()
        }
    }

    override fun readItems(){
        val thread: Thread = Thread(Runnable {
            clusterManager!!.clearItems()
            val items: List<SalesOutletResult> = salesOutletResponse!!.resultObject!!
            for (i in 0..10) {
                val offset: Double = i / 60.0
                for (j in items) {
                    if(j.longitude != null && j.latitude != null) {
                        val position: LatLng = j.position
                        val lat: Double = position.latitude + offset
                        val lng: Double = position.longitude + offset
                        val offsetItem = SalesOutletResult(
                            j.salesOutletId, j.name,
                            j.address,
                            LatLng(lat, lng)
//                            , j.isAcceptOrders
//                            ,j.stateCode, j.statusCode,
//                            j.importSequenceNumber, j.createdOn,
//                            j.createdBy, j.modifiedOn, j.modifiedBy
                        )
                        clusterManager!!.addItem(offsetItem)
                    }
                }
            }
        })
        thread.start()
//        thread.isDaemon = true

    }


    override fun setUpMap(mapType: Int) {
        gps = GPS(App.appComponent.context())

        mMap?.mapType = mapType
        val settings = mMap?.uiSettings
        settings?.setAllGesturesEnabled(true)
        settings?.isMyLocationButtonEnabled = true
        settings?.isMapToolbarEnabled = true

        val fine = Manifest.permission.ACCESS_FINE_LOCATION
        val coarse = Manifest.permission.ACCESS_COARSE_LOCATION
        val granted = PackageManager.PERMISSION_GRANTED

        applyCameraUpdate(CameraUpdateFactory.newLatLngZoom(LatLng(43.238949, 76.889709), 14f))

        if (ActivityCompat.checkSelfPermission(App.appComponent.context(), fine) != granted && ActivityCompat.checkSelfPermission(
                App.appComponent.context(),
                coarse
            ) != granted
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(fine, coarse), LOCATION_REQUEST)
            }
        } else {
            mLocationMapPresenter.goToMyLocation()
        }

        clusterManager = MyClusterManager(context!!, mMap!!)
        clusterManager!!.setOnClusterClickListener (this)
        clusterManager!!.setOnClusterItemClickListener (this)
        var salesClusterRenderer = SalesClusterRenderer(context, mMap, clusterManager)
//        salesClusterRenderer.minClusterSize = 5
        clusterManager!!.renderer = salesClusterRenderer
        mMap?.setOnMarkerClickListener(clusterManager)
        mMap?.setOnCameraMoveStartedListener {
            selectedPoint = null
//            showLoading()
        }

        mMap?.setOnCameraIdleListener(clusterManager)
    }

    override fun goToMyLocation()
    {
        if(gps?.canGetLocation() == true) {
            mMap?.isMyLocationEnabled = true
            val myLocation = mMap?.myLocation ?: gps!!.location

            if (myLocation != null) {
                applyCameraUpdate(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(myLocation.latitude, myLocation.longitude), 14f
                    )
                )
            }
        }
    }

    override fun getSalesOutletBoundaries(){

        if(mMap!!.cameraPosition.zoom.toInt() <= 11){
            Toast.makeText(context!!, Constants.zoomFarWarning, Toast.LENGTH_SHORT).show()
        }else{



            if(selectedPoint != null){
                if(PolyUtil.containsLocation(selectedPoint, po.points, true)) {
//                    Toast.makeText(context!!, selectedPoint!!.longitude.toString() + " " + selectedPoint!!.latitude.toString(), Toast.LENGTH_SHORT).show()
                }
                else{
                    clusteringMap()
                }

            }
            else{
                mLocationMapPresenter.drawMarkers()
            }
        }
    }

    fun clusteringMap(){

        po.points.clear()
        mLocationMapPresenter.addToPo()
        mLocationMapPresenter.drawMarkers()
    }

    override fun drawMarkers(){
        mMap!!.clear()
        mLocationMapPresenter.createBoundaries(
            Points(mMap!!.projection.visibleRegion.farLeft.longitude, mMap!!.projection.visibleRegion.farLeft.latitude),
            Points(mMap!!.projection.visibleRegion.nearLeft.longitude, mMap!!.projection.visibleRegion.nearLeft.latitude),
            Points(mMap!!.projection.visibleRegion.nearRight.longitude, mMap!!.projection.visibleRegion.nearRight.latitude),
            Points(mMap!!.projection.visibleRegion.farRight.longitude, mMap!!.projection.visibleRegion.farRight.latitude)
        )
    }


    override fun addToPo(){
        val farLeft = mMap!!.projection.visibleRegion.farLeft
        val nearLeft =   mMap!!.projection.visibleRegion.nearLeft
        val nearRight = mMap!!.projection.visibleRegion.nearRight
        val farRight = mMap!!.projection.visibleRegion.farRight

        po.add(farLeft,
            nearLeft,
            nearRight,
            farRight
        )
    }

    @Throws(IOException::class)
    private fun getAddressByCoordinates(point: LatLng): String? {
        val geocoder = Geocoder(activity, Locale.getDefault())
        val addresses: List<Address>

        addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1)

        return if (addresses.isNotEmpty()) {
            /*String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();*/
            addresses[0].getAddressLine(0)
        } else null

    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        if (requestCode == LOCATION_REQUEST) {
            var granted = false
            for (result in grantResults) {
                granted = granted or (result == PackageManager.PERMISSION_GRANTED)
                if (granted) break
            }
            if (granted && mMap != null) {
                mLocationMapPresenter.goToMyLocation()
            }
        }
    }

    private fun applyCameraUpdate(cameraUpdate: CameraUpdate) {
        if (binding.flMain.width > 1 && binding.flMain.height > 1) {
            mMap?.moveCamera(cameraUpdate)
        } else {
            binding.flMain.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                override fun onLayoutChange(
                    view: View,
                    i: Int,
                    i1: Int,
                    i2: Int,
                    i3: Int,
                    i4: Int,
                    i5: Int,
                    i6: Int,
                    i7: Int
                ) {
                    mMap?.moveCamera(cameraUpdate)
                    binding.flMain.removeOnLayoutChangeListener(this)
                }
            })
        }
    }

    override fun onClusterItemClick(p0: SalesOutletResult?): Boolean {
        mLocationMapPresenter.setSalesOutler(p0!!)
        binding.txtAddress.text = p0.title + "\n" + p0.snippet
//        binding.txtAddress.text = "Test"

        mLocationMapPresenter.isClickedMarker = true

        mLocationMapPresenter.setObserveForSubmitButton(true)
        return false
    }

    override fun onClusterClick(p0: Cluster<SalesOutletResult>?): Boolean {
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        mLocationMapPresenter.getSalesOutlenByName(query!!)
//                mLocationMapPresenter.setObserveForCancellSearchButton(false)
//                mLocationMapPresenter.setObserveForSubmitButton(false)
//        Timber.i("SimpleSearchView", "Submit:" + query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
//        Timber.i("SimpleSearchView", "Text changed:" + newText)
        return false
    }

    override fun onQueryTextCleared(): Boolean {
//        Toast.makeText(context!!, "Text cleared", Toast.LENGTH_SHORT).show()
//        Timber.i("SimpleSearchView", "Text cleared")
        return false
    }


    inner class MyClusterManager<T: SalesOutletResult>(var context: Context, var map: GoogleMap): ClusterManager<T>(context, map){
        override fun onCameraIdle() {
            super.onCameraIdle()
//            clusterManager!!.cluster()
            selectedPoint = mMap?.cameraPosition?.target
            try {
                if(mLocationMapPresenter.observeForCancellSearchButton().value != false) {

                    mLocationMapPresenter.getSalesOutletBoundaries()
                }
                if(mLocationMapPresenter.isClickedMarker!!)
                    mLocationMapPresenter.isClickedMarker = false
                else {
                    binding.txtAddress.text = getAddressByCoordinates(selectedPoint!!)
                    mLocationMapPresenter.setObserveForSubmitButton(false)
                }

            } catch (e: Exception) {
                txtAddress.text = null
            }
        }
    }
}