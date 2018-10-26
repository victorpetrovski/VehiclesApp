package victorpetrovski.com.victortaxi.di.module

import dagger.Module
import dagger.Provides
import victorpetrovski.com.data.remote.VehiclesRemoteImpl
import victorpetrovski.com.data.remote.api.MyTaxiApi
import victorpetrovski.com.data.remote.api.MyTaxiService
import victorpetrovski.com.data.repository.VehiclesDataRepository
import victorpetrovski.com.data.repository.gateway.VehiclesRemote
import victorpetrovski.com.domain.gateway.VehicleRepository
import victorpetrovski.com.domain.schedulers.Schedulers
import victorpetrovski.com.victortaxi.BuildConfig
import victorpetrovski.com.victortaxi.BuildConfig.BASE_URL
import victorpetrovski.com.victortaxi.util.schedulers.AppSchedulers
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    fun provideVehicleRepository(vehiclesDataRepository: VehiclesDataRepository): VehicleRepository = vehiclesDataRepository

    @Provides
    fun provideVehiclesRemote(vehiclesRemoteImpl: VehiclesRemoteImpl): VehiclesRemote = vehiclesRemoteImpl

    @Provides
    @Singleton
    fun provideMyTaxiService(): MyTaxiService {
        return MyTaxiApi(BASE_URL, BuildConfig.DEBUG)
    }

    @Provides
    fun provideSchedulers(): Schedulers {
        return AppSchedulers()
    }

}