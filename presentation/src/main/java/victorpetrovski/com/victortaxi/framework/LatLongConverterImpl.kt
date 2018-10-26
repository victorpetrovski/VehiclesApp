package victorpetrovski.com.victortaxi.framework

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import victorpetrovski.com.victortaxi.gateway.LatLongConverter
import java.util.*
import javax.inject.Inject

class LatLongConverterImpl @Inject constructor(private val context : Context) : LatLongConverter {

    val TAG = "LatLongConverterRepo"

    override fun extractAddressFromCoordinates(lat: Double, long: Double): String {


        val listOfAddress: MutableList<Address>

        Log.v(TAG,"Started Executing extractAddressFromCoordinates for $lat & $long")

        val geocode = Geocoder(context, Locale.getDefault())

        return try {
            listOfAddress = geocode.getFromLocation(lat, long, 1)

            val address = listOfAddress[0]


            Log.v(TAG,"FINISHED Executing extractAddressFromCoordinates for $lat & $long")
            address.getAddressLine(0)

        }catch ( e : Exception){
            Log.v(TAG,"Exception Executing extractAddressFromCoordinates for $lat & $long")

            "Oops... Unable to find Address for given coordinates"
        }
    }

}