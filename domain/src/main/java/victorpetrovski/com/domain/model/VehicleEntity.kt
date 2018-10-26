package victorpetrovski.com.domain.model

data class VehicleEntity(
        var id : Long,
        var latitude : Double,
        var longitude: Double,
        var type : String,
        var rotation : Float)