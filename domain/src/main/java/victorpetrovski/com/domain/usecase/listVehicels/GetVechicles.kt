package victorpetrovski.com.domain.usecase.listVehicels

import io.reactivex.Observable
import victorpetrovski.com.domain.model.VehicleEntity
import victorpetrovski.com.domain.gateway.VehicleRepository
import victorpetrovski.com.domain.schedulers.Schedulers
import victorpetrovski.com.domain.usecase.base.ObservableUseCase
import javax.inject.Inject

open class GetVechicles @Inject constructor(
        private val vehicleRepository: VehicleRepository,
        private val schedulers: Schedulers
) : ObservableUseCase<List<VehicleEntity>, GetVechicles.Params>(schedulers) {

    public override fun buildUseCaseObservable(params: Params?): Observable<List<VehicleEntity>> {

        params?.let { return vehicleRepository.getAllVehiclesInRange(params.p1Lat, params.p1Long, params.p2Lat, params.p2Long) }

        throw IllegalArgumentException("Longitude and Latitude must be provided!")

    }


    data class Params constructor(val p1Lat: Double,
                                  val p1Long: Double,
                                  val p2Lat: Double,
                                  val p2Long: Double

    ) {
        companion object {
            fun forLocation(p1Lat: Double,
                            p1Long: Double,
                            p2Lat: Double,
                            p2Long: Double): Params {
                return Params(p1Lat, p1Long, p2Lat, p2Long)
            }
        }
    }
}
