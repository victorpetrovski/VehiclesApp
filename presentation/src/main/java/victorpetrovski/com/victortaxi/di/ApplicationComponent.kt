package victorpetrovski.com.victortaxi.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import victorpetrovski.com.victortaxi.di.module.*
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    ActivityModule::class,
    AppModule::class,
    DataModule::class,
    ViewModelModule::class,
    FragmentBuildersModule::class])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<DaggerApplication>()

}