package kz.dragau.larek.ui.fragment.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.map.LocationMapView
import kz.dragau.larek.presentation.presenter.map.LocationMapPresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.fragment_location_map.*
import kz.dragau.larek.App
import kz.dragau.larek.databinding.FragmentLocationMapBinding
import photograd.kz.photograd.ui.fragment.BaseMvpFragment
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.io.IOException
import java.util.*
import javax.inject.Inject

class LocationMapFragment : BaseMvpFragment(), LocationMapView {
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

    @ProvidePresenter
    fun providePresenter(): LocationMapPresenter
    {
        return LocationMapPresenter(router)
    }

    private val LOCATION_REQUEST = 665
    private var mMap: GoogleMap? = null
    private var selectedPoint: LatLng? = null
    private var gps: GPS? = null


    lateinit var binding: FragmentLocationMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
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

         val autocompleteFragment = childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.ADDRESS))

        autocompleteFragment.setOnPlaceSelectedListener(object: PlaceSelectionListener{
            override fun onPlaceSelected(place: Place) {
                //txtView.setText(place.name +","+place.id)
                Timber.i(TAG, "Place: " + place.name + ", " + place.id)
            }

            override fun onError(status: Status)
            {
                showError("An error occurred: $status")
            }
        })

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

        //just shortcuts
        val fine = Manifest.permission.ACCESS_FINE_LOCATION
        val coarse = Manifest.permission.ACCESS_COARSE_LOCATION
        val granted = PackageManager.PERMISSION_GRANTED

        //TODO: add previous start location
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
            showLoading()
        }

        mMap?.setOnCameraIdleListener {
            selectedPoint = mMap?.cameraPosition?.target
            try {
                binding.txtAddress.text = getAddressByCoordinates(selectedPoint!!)
            } catch (e: Exception) {
                txtAddress.text = null
            }
        }
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
}
