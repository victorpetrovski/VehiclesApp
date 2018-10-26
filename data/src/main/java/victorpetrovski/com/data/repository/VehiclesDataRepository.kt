package victorpetrovski.com.data.repository

import victorpetrovski.com.data.repository.gateway.VehiclesRemote
import victorpetrovski.com.domain.gateway.VehicleRepository
import javax.inject.Inject

class VehiclesDataRepository @Inject constructor(
        private val vehiclesRemote: VehiclesRemote
) : VehicleRepository {

    override fun getAllVehiclesInRange(p1Lat: Double, p1Long: Double, p2Lat: Double, p2Long: Double)
            = vehiclesRemote.getVehiclesInRange(p1Lat, p1Long, p2Lat, p2Long)

}