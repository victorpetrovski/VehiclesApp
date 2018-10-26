package victorpetrovski.com.victortaxi.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import victorpetrovski.com.victortaxi.features.listOfVehicles.VehiclesListFragment
import victorpetrovski.com.victortaxi.features.vehiclesMap.MapsFragment

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    internal abstract fun contributeVehiclesListFragmentFragment(): VehiclesListFragment

    @ContributesAndroidInjector
    internal abstract fun contributeMapFragmentFragment(): MapsFragment


}