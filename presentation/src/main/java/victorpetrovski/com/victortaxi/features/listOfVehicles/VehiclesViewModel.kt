package victorpetrovski.com.victortaxi.features.listOfVehicles

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import victorpetrovski.com.domain.model.VehicleEntity
import victorpetrovski.com.domain.schedulers.Schedulers
import victorpetrovski.com.domain.usecase.listVehicels.GetVechicles
import victorpetrovski.com.victortaxi.mapper.EntityMapper
import victorpetrovski.com.victortaxi.model.VehicleView
import victorpetrovski.com.victortaxi.state.ViewResource
import victorpetrovski.com.victortaxi.state.ViewState
import javax.inject.Inject

class VehiclesViewModel @Inject constructor(
        private val getVehicles: GetVechicles,
        private val entityMapper: EntityMapper,
        private val schedulers: Schedulers
) : ViewModel() {


    /***
     * Hardcoded params for Hamburg Area as per Project Requirements
     */
    private val params = GetVechicles.Params(53.694865, 9.757589, 53.394655, 10.099891)

    private val liveData: MutableLiveData<ViewResource<List<VehicleView>>> = MutableLiveData()

    private var vehiclesList = mutableListOf<VehicleView>()

    fun getVehiclesLiveData() = liveData

    fun loadVehiclesList() {
        liveData.postValue(ViewResource(ViewState.LOADING, vehiclesList, null))
        getVehicles.execute(ListVehiclesSubscriber(), params)
    }

    override fun onCleared() {
        getVehicles.dispose()
        super.onCleared()
    }

    inner class ListVehiclesSubscriber : DisposableObserver<List<VehicleEntity>>() {

        override fun onNext(t: List<VehicleEntity>) {
            /**
             * Once we get the list of VehicleEntity, we will try to extract the address from the coordinates
             * We need to make this operation on a background thread
             * */

//            Observable.fromCallable {  (  t.map { entityMapper.mapFrom(it) } ) }
//                    .observeOn(schedulers.observeOn)
//                    .subscribeOn(schedulers.subscribeOn)
//                    .subscribe({
//                        liveData.postValue(ViewResource(ViewState.SUCCESS, it, null))
//                    }, {
//                        onError(it)
//                    })

            Observable.just(t)
                    .subscribeOn(schedulers.subscribeOn)
                    .flatMapIterable {it
                    }.flatMap {
                        Observable.fromCallable { entityMapper.mapFrom(it) }.subscribeOn(schedulers.subscribeOn)
                    }
                    .toList()
                    .observeOn(schedulers.observeOn)
                    .subscribe({
                        vehiclesList = it
                        liveData.postValue(ViewResource(ViewState.SUCCESS, it, null))
                    }, {
                        onError(it)
                    })

        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {
            liveData.postValue(ViewResource(ViewState.ERROR, null, e.localizedMessage))
        }

    }
}