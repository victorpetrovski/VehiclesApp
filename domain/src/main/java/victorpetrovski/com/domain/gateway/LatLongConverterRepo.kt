package victorpetrovski.com.domain.gateway

import io.reactivex.Observable

interface LatLongConverterRepo {

    fun extractAddressFromCoordinates(lat : Double, long : Double) : Observable<String>
}