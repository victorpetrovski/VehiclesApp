package victorpetrovski.com.victortaxi.framework

import android.content.Context
import android.location.Address
import android.location.Geocoder
import io.reactivex.Observable
import victorpetrovski.com.domain.gateway.LatLongConverterRepo
import java.util.*
import javax.inject.Inject

class LatLongConverterRepository @Inject constructor(private val context: Context) : LatLongConverterRepo {


    override fun extractAddressFromCoordinates(lat: Double, long: Double) = Observable.fromCallable {
            val listOfAddress: MutableList<Address>

            val geocode = Geocoder(context, Locale.getDefault())

            listOfAddress = geocode.getFromLocation(lat, long, 1)

            val address = listOfAddress[0]

            address.getAddressLine(0)
        }


}