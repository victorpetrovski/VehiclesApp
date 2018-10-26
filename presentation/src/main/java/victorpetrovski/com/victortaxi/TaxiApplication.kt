package victorpetrovski.com.victortaxi

import victorpetrovski.com.victortaxi.di.DaggerApplication
import victorpetrovski.com.victortaxi.di.extensions.applyAutoInjector

class TaxiApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        applyAutoInjector()
    }

}