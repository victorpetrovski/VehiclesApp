package victorpetrovski.com.victortaxi.util.schedulers

import io.reactivex.Scheduler
import victorpetrovski.com.domain.schedulers.Schedulers
import javax.inject.Inject

class AppSchedulers @Inject constructor() : Schedulers {

    override val computation: Scheduler
        get() =  io.reactivex.schedulers.Schedulers.computation()


    override val subscribeOn: Scheduler
        get() = io.reactivex.schedulers.Schedulers.io()

    override val observeOn: Scheduler
        get() = io.reactivex.android.schedulers.AndroidSchedulers.mainThread()

}


