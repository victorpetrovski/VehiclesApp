package victorpetrovski.com.victortaxi.mapper

import victorpetrovski.com.victortaxi.model.VehicleType
import javax.inject.Inject

open class VehicleTypeMapper @Inject constructor() : ViewMapper<String, VehicleType> {

    override fun mapFrom(entity: String): VehicleType {
        if (entity.equals("taxi", ignoreCase = true)) return VehicleType.TAXI
        else if (entity.equals("POOLING", ignoreCase = true)) return VehicleType.POOLING
        return VehicleType.UNKNOWN
    }
}