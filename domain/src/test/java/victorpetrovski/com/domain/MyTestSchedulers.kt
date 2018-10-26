package victorpetrovski.com.domain

import io.reactivex.Scheduler
import victorpetrovski.com.domain.schedulers.Schedulers


class MyTestSchedulers : Schedulers {
    override val computation: Scheduler
        get() = io.reactivex.schedulers.Schedulers.trampoline()

    override val subscribeOn: Scheduler
        get() = io.reactivex.schedulers.Schedulers.trampoline()

    override val observeOn: Scheduler
        get() = io.reactivex.schedulers.Schedulers.trampoline()
}