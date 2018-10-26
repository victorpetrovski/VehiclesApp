package victorpetrovski.com.data.mapper

import victorpetrovski.com.data.model.VehicleData
import victorpetrovski.com.domain.model.VehicleEntity
import javax.inject.Inject

open class ModelEntityMapper @Inject constructor() : ModelMapper<VehicleData, VehicleEntity> {

    override fun mapFromModel(model: VehicleData): VehicleEntity {
        return VehicleEntity(model.vehicleId, model.cords.latitude, model.cords.longitude, model.type, model.heading)
    }
}