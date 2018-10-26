package victorpetrovski.com.victortaxi.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import victorpetrovski.com.victortaxi.features.MainActivity

@Module
abstract class ActivityModule {


    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity


}
