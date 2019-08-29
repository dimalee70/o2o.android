package kz.dragau.larek.ui.fragment.map

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.map.LocationMapView
import kz.dragau.larek.presentation.presenter.map.LocationMapPresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ferfalk.simplesearchview.SimpleSearchView
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.maps.android.PolyUtil
import kotlinx.android.synthetic.main.activity_store.*
import kotlinx.android.synthetic.main.fragment_location_map.*
import kz.dragau.larek.App
import kz.dragau.larek.Constants
import kz.dragau.larek.api.response.SalesOutletResponse
import kz.dragau.larek.databinding.FragmentLocationMapBinding
import kz.dragau.larek.models.objects.Points
import kz.dragau.larek.presentation.presenter.map.SaleSelector
import photograd.kz.photograd.ui.fragment.BaseMvpFragment
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.io.IOException
import java.lang.reflect.Field
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class LocationMapFragment : BaseMvpFragment(), LocationMapView, GoogleMap.OnMarkerClickListener {


    private val LOCATION_REQUEST = 665
    private var mMap: GoogleMap? = null
    private var selectedPoint: LatLng? = null
    private var gps: GPS? = null
    private var searchMenu: Menu? = null
    private var item_search: MenuItem? = null
    private val lifecycleRegistry = LifecycleRegistry(this)
    private val po: PolygonOptions = PolygonOptions()

    companion object {
        const val TAG = "LocationMapFragment"

        fun newInstance(): LocationMapFragment {
            val fragment: LocationMapFragment = LocationMapFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var mLocationMapPresenter: LocationMapPresenter

    @Inject
    lateinit var saleSelector: SaleSelector

    @ProvidePresenter
    fun providePresenter(): LocationMapPresenter
    {
        return LocationMapPresenter(router, saleSelector)
    }



    lateinit var binding: FragmentLocationMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)

        mLocationMapPresenter.attachLifecycle(lifecycleRegistry)

//        val observer = Observer<SalesOutletResponse> { responce -> showSalesOutlet(responce)}
        mLocationMapPresenter.observeForSalesOutletResponse()
            .observe(this, Observer      {
                    response -> response.let { showSalesOutlet(response) }
            })

        mLocationMapPresenter.observeForSalesOutletResponseBoundary()
            .observe(this, Observer{
                response -> response.let{ showSalesOutlet(response)}
            })

        mLocationMapPresenter.observeForCancellSearchButton()
            .observe(this, Observer {
                response -> response.let { if (response) getSalesOutletBoundaries() else mMap!!.clear() }
            })

        mLocationMapPresenter.obseverForSubmitButton()
            .observe(this, Observer {
                response -> response.let { if (response) binding!!.btnProceed.visibility = View.VISIBLE
                    else binding.btnProceed.visibility = View.GONE}
            })
        activity!!.toolbarCl.visibility = View.VISIBLE
        activity!!.searchView.setOnQueryTextListener(object: SimpleSearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                mLocationMapPresenter.getSalesOutlenByName(query!!)
                mLocationMapPresenter.setObserveForCancellSearchButton(false)
                mLocationMapPresenter.setObserveForSubmitButton(false)
                Timber.i("SimpleSearchView", "Submit:" + query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Timber.i("SimpleSearchView", "Text changed:" + newText)
                return false
            }

            override fun onQueryTextCleared(): Boolean {
                Toast.makeText(context!!, "Text cleared", Toast.LENGTH_SHORT).show()
                Timber.i("SimpleSearchView", "Text cleared")
                return false
            }
        })
    }

//    private fun mark() {
//
//        mMap?.addMarker(MarkerOptions()
//            .position(LatLng(43.253874, 76.955835))
//            .icon(BitmapDescriptorFactory.defaultMarker
//                (BitmapDescriptorFactory.HUE_GREEN)))
//    }

    private fun showSalesOutlet(salesOutletResponse: SalesOutletResponse?) {
        mMap?.clear()

        if(salesOutletResponse!!.resultObject != null && salesOutletResponse.resultObject!!.size > 0) {
            salesOutletResponse!!.resultObject!!.forEach {
                if (it.latitude != null && it.longitude != null) {
                    mMap?.addMarker(
                        MarkerOptions()
                            .position(LatLng(it.latitude, it.longitude))
                            .title(it.name).snippet(it.address).visible(true)
                            .anchor(0f, 0.5f)
                            .icon(
                                bitmapDescriptorFromVector(context!!, R.drawable.ic_marker)
                            )
                    )!!.showInfoWindow()
                }
            }
        }else{
            Toast.makeText(context!!, "Ничего не найдено", Toast.LENGTH_SHORT).show()
        }
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

    override fun changeMapType(mapType: Int) {
        mMap?.mapType = mapType
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
            goToMyLocation()
        }

        mMap?.setOnCameraMoveStartedListener {
            selectedPoint = null
//            showLoading()
        }

        mMap!!.setOnMarkerClickListener(this)

        mMap?.setOnCameraIdleListener {
            selectedPoint = mMap?.cameraPosition?.target
            try {
                if(mLocationMapPresenter.observeForCancellSearchButton().value != false) {

                    getSalesOutletBoundaries()
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

    override fun onMarkerClick(p0: Marker?): Boolean {
        mLocationMapPresenter.setSalesOutler(p0!!.title, p0.title, p0.snippet)
        binding.txtAddress.text = p0.title + "\n" + p0.snippet
//        binding.txtAddress.text = "Test"

        mLocationMapPresenter.isClickedMarker = true

        mLocationMapPresenter.setObserveForSubmitButton(true)
//        Toast.makeText(context!!, p0!!.title + " " + p0.snippet, Toast.LENGTH_SHORT).show()
        return false
    }

    private fun getSalesOutletBoundaries(){

        if(mMap!!.cameraPosition.zoom.toInt() <= 11){
            Toast.makeText(context!!, Constants.zoomFarWarning, Toast.LENGTH_SHORT).show()
        }else{



            if(selectedPoint != null){
                if(PolyUtil.containsLocation(selectedPoint, po.points, true)) {
//                    Toast.makeText(context!!, selectedPoint!!.longitude.toString() + " " + selectedPoint!!.latitude.toString(), Toast.LENGTH_SHORT).show()
                }
                else{

                    po.points.clear()
                    addToPo()
                    drawMarkers()
                }

            }
            else{
                drawMarkers()
            }
        }
    }

    private fun addToPo(){
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

    private fun drawMarkers(){
        mMap!!.clear()
        mLocationMapPresenter.createBoundaries(
            Points(mMap!!.projection.visibleRegion.farLeft.longitude, mMap!!.projection.visibleRegion.farLeft.latitude),
            Points(mMap!!.projection.visibleRegion.nearLeft.longitude, mMap!!.projection.visibleRegion.nearLeft.latitude),
            Points(mMap!!.projection.visibleRegion.nearRight.longitude, mMap!!.projection.visibleRegion.nearRight.latitude),
            Points(mMap!!.projection.visibleRegion.farRight.longitude, mMap!!.projection.visibleRegion.farRight.latitude)
        )
    }

    private fun goToMyLocation()
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

    private fun showLoading() {
        btnProceed.visibility = View.GONE
        txtMessage.text = null
        txtAddress.text = null
        clLoading.visibility = View.VISIBLE
        clLoading.visibility = View.GONE

        btnProceed.isEnabled = false
    }

    private fun hideLoading() {
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

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        if (requestCode == LOCATION_REQUEST) {
            var granted = false
            for (result in grantResults) {
                granted = granted or (result == PackageManager.PERMISSION_GRANTED)
                if (granted) break
            }
            if (granted && mMap != null) {
                goToMyLocation()
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

    override fun onDestroy() {
        super.onDestroy()
        activity!!.toolbarCl.visibility = View.GONE
        mLocationMapPresenter.detachLifecycle(lifecycleRegistry )
    }

    fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}