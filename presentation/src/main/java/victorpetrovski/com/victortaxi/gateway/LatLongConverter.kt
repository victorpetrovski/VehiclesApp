package victorpetrovski.com.victortaxi.gateway

interface LatLongConverter {

    fun extractAddressFromCoordinates(lat : Double, long : Double) : String
}