package victorpetrovski.com.victortaxi.model

data class VehicleView(
        var id : Long,
        var latitude : Double,
        var longitude: Double,
        var locationAddress : String,
        var heading : Float,
        var type : VehicleType)

