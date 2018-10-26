package victorpetrovski.com.data.remote

import io.reactivex.Observable
import victorpetrovski.com.data.mapper.ModelEntityMapper
import victorpetrovski.com.data.remote.api.MyTaxiService
import victorpetrovski.com.data.repository.gateway.VehiclesRemote
import victorpetrovski.com.domain.model.VehicleEntity
import javax.inject.Inject

class VehiclesRemoteImpl @Inject constructor(
        private val myTaxiService: MyTaxiService,
        private val modelEntityMapper: ModelEntityMapper) : VehiclesRemote {

    override fun getVehiclesInRange(p1Lat: Double, p1Long: Double, p2Lat: Double, p2Long: Double): Observable<List<VehicleEntity>> {
        return myTaxiService.searchRepositories(p1Lat, p1Long, p2Lat, p2Long)
                .map {
                    it.vehicleList.map { modelEntityMapper.mapFromModel(it) }
                }
    }

}