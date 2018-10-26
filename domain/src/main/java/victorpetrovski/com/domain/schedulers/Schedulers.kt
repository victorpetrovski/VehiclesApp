package victorpetrovski.com.domain.schedulers

import io.reactivex.Scheduler

interface Schedulers {

    val subscribeOn: Scheduler

    val observeOn: Scheduler

    val computation: Scheduler

}
