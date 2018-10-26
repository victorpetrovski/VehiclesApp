package victorpetrovski.com.data.model

import com.google.gson.annotations.SerializedName

data class VehicleResponseData(
        @SerializedName("poiList") val vehicleList : List<VehicleData>)
