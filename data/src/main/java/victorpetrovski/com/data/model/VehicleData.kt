package victorpetrovski.com.data.model

import com.google.gson.annotations.SerializedName

data class VehicleData(
        @SerializedName("coordinate") val cords: CoordinateData,
        @SerializedName("id") val vehicleId: Long,
        @SerializedName("fleetType") val type : String,
        @SerializedName("heading") val heading : Float)