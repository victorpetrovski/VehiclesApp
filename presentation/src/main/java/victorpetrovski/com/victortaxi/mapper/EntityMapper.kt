package victorpetrovski.com.victortaxi.mapper

import victorpetrovski.com.domain.model.VehicleEntity
import victorpetrovski.com.victortaxi.gateway.LatLongConverter
import victorpetrovski.com.victortaxi.model.VehicleView
import javax.inject.Inject

open class EntityMapper @Inject constructor(
        private val vehicleTypeMapper: VehicleTypeMapper,
        private val latLongConverter: LatLongConverter
) : ViewMapper<VehicleEntity, VehicleView> {

    override fun mapFrom(entity: VehicleEntity): VehicleView {
        return VehicleView(entity.id, entity.latitude, entity.longitude,
                latLongConverter.extractAddressFromCoordinates(entity.latitude, entity.longitude),
                entity.rotation,
                vehicleTypeMapper.mapFrom(entity.type))
    }
}