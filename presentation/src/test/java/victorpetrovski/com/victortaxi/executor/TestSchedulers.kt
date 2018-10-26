package victorpetrovski.com.victortaxi.executor

import io.reactivex.Scheduler
import victorpetrovski.com.domain.schedulers.Schedulers

class TestSchedulers : Schedulers {

    override val computation: Scheduler
        get() = io.reactivex.schedulers.Schedulers.trampoline()

    override val subscribeOn: Scheduler
        get() = io.reactivex.schedulers.Schedulers.trampoline()

    override val observeOn: Scheduler
        get() = io.reactivex.schedulers.Schedulers.trampoline()
}