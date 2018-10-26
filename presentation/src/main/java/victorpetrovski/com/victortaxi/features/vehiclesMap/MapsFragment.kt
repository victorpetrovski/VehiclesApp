package victorpetrovski.com.victortaxi.features.vehiclesMap

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import victorpetrovski.com.victortaxi.R
import victorpetrovski.com.victortaxi.base.BaseFragment
import victorpetrovski.com.victortaxi.state.ViewState
import victorpetrovski.com.victortaxi.util.extension.myObserver
import javax.inject.Inject


class MapsFragment : BaseFragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    val MY_LOCATION_REQUEST_CODE = 14

    @Inject
    lateinit var vehiclesMapViewModel: VehiclesMapViewModel

    override fun getLayout() = R.layout.activity_maps

    override fun setupViews(savedInstanceState : Bundle?) {

        val mapFragment = childFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        retainInstance = true
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        showMyLocation()
    }

    private fun subscribeViewModel() {
        vehiclesMapViewModel.getVehiclesLiveData().myObserver(this) {

            when (it?.status) {
                ViewState.ERROR -> {
                    showError(it.message)
                }
                ViewState.SUCCESS -> {
                    it.data?.let {
                        mMap.clear()
                        it.forEach {
                            mMap.addMarker(it.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_taxi_marker)))
                        }
                    }
                    dismissError()
                }
                ViewState.LOADING -> {

                }
            }

        }

        mMap.setOnCameraMoveListener {
            updateMap()
        }

        mMap.setOnMarkerClickListener { marker ->
            zoomInCamera(marker.position, 19f)
            true
        }
    }

    var snackbar: Snackbar? = null

    private fun showError(message: String?) {
        snackbar = Snackbar.make(view!!, message.toString(), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.close)) {
                    snackbar?.dismiss()
                }
                .setActionTextColor(resources.getColor(android.R.color.holo_red_light))

        snackbar?.show()
    }

    private fun dismissError() {
        snackbar?.dismiss()
    }

    private fun updateMap() {
        val bounds = mMap.projection.visibleRegion.latLngBounds

        val p1Lat = bounds.northeast.latitude
        val p1Lon = bounds.northeast.longitude

        val p2Lat = bounds.southwest.latitude
        val p2Lon = bounds.southwest.longitude

        vehiclesMapViewModel.onMapMoved(p1Lat, p1Lon, p2Lat, p2Lon)
    }

    private fun showMyLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
            zoomInCameraToMyLocationOnceAvailable()
            subscribeViewModel()
        } else {
            requestPermissions( //Method of Fragment
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_LOCATION_REQUEST_CODE
            )
        }
    }

    private fun zoomInCameraToMyLocationOnceAvailable() {

        if (locationPermissionGranted()) {

            val locationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager
            val criteria = Criteria()
            val provider = locationManager.getBestProvider(criteria, true)
            var location = locationManager.getLastKnownLocation(provider)

            location?.let {
                val latitude = location.latitude
                val longitude = location.longitude

                zoomInCamera(LatLng(latitude, longitude))
            }
        }
    }

    private fun zoomInCamera(cords: LatLng, zoom: Float = 15f) {
        val location = CameraUpdateFactory.newLatLngZoom(cords, zoom)
        mMap.animateCamera(location)
    }

    private fun locationPermissionGranted() =
            ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.size == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showMyLocation()
            } else {
                // Permission was denied. Display an error message.
            }
        }
    }


}
