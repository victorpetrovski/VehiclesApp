package victorpetrovski.com.domain.usecase.base

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import victorpetrovski.com.domain.schedulers.Schedulers

abstract class ObservableUseCase<T, in Params> constructor(
        private val schedulers: Schedulers) {

    private val disposables = CompositeDisposable()

    protected abstract fun buildUseCaseObservable(params: Params? = null): Observable<T>

    open fun execute(singleObserver: DisposableObserver<T>, params: Params? = null): Observable<T> {
        val single = this.buildUseCaseObservable(params)
                .subscribeOn(schedulers.subscribeOn)
                .observeOn(schedulers.observeOn)
        addDisposable(single.subscribeWith(singleObserver))
        return single
    }

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun dispose() {
        if (!disposables.isDisposed) disposables.dispose()
    }

}