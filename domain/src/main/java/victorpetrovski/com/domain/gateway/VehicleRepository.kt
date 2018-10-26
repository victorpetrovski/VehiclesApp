package victorpetrovski.com.domain.gateway

import io.reactivex.Observable
import victorpetrovski.com.domain.model.VehicleEntity

interface VehicleRepository {

    fun getAllVehiclesInRange(p1Lat : Double, p1Long : Double, p2Lat : Double, p2Long : Double) : Observable<List<VehicleEntity>>

}