package victorpetrovski.com.victortaxi.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import victorpetrovski.com.victortaxi.framework.LatLongConverterImpl
import victorpetrovski.com.victortaxi.gateway.LatLongConverter
import javax.inject.Singleton

@Module
class FrameworkModule {

    @Provides
    @Singleton
    fun provideAddressProvider(  context: Context ) : LatLongConverter = LatLongConverterImpl(context)

}