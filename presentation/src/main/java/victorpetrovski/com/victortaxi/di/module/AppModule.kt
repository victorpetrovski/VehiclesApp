package victorpetrovski.com.victortaxi.di.module

import android.content.Context
import dagger.Binds
import dagger.Module
import victorpetrovski.com.victortaxi.di.DaggerApplication

@Module
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: DaggerApplication) : Context

}