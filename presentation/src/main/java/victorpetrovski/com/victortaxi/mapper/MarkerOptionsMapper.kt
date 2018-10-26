package victorpetrovski.com.victortaxi.mapper

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import victorpetrovski.com.domain.model.VehicleEntity
import javax.inject.Inject

open class MarkerOptionsMapper @Inject constructor() : ViewMapper<VehicleEntity, MarkerOptions> {

    override fun mapFrom(entity: VehicleEntity): MarkerOptions {
        return MarkerOptions().position(LatLng(entity.latitude, entity.longitude)).rotation(entity.rotation)
    }
}