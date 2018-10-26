package victorpetrovski.com.victortaxi.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import victorpetrovski.com.victortaxi.di.ViewModelKey
import victorpetrovski.com.victortaxi.di.extensions.ViewModelProviderFactory
import victorpetrovski.com.victortaxi.features.listOfVehicles.VehiclesViewModel

@Module(includes = arrayOf(FrameworkModule::class))
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(VehiclesViewModel::class)
    internal abstract fun  bindsGameRunViewModel(gameRunViewModel: VehiclesViewModel) : ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelProviderFactory) : ViewModelProvider.Factory


}