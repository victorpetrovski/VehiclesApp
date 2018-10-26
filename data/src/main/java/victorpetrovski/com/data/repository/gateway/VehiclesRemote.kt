package victorpetrovski.com.data.repository.gateway

import io.reactivex.Observable
import victorpetrovski.com.domain.model.VehicleEntity

interface VehiclesRemote{

    fun getVehiclesInRange(p1Lat : Double, p1Long : Double, p2Lat : Double, p2Long : Double) : Observable<List<VehicleEntity>>

}