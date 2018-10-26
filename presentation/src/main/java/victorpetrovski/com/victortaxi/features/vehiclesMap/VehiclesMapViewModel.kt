package victorpetrovski.com.victortaxi.features.vehiclesMap

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.observers.DisposableObserver
import io.reactivex.subjects.PublishSubject
import victorpetrovski.com.domain.model.VehicleEntity
import victorpetrovski.com.domain.usecase.listVehicels.GetVechicles
import victorpetrovski.com.victortaxi.mapper.MarkerOptionsMapper
import victorpetrovski.com.victortaxi.state.ViewResource
import victorpetrovski.com.victortaxi.state.ViewState
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class VehiclesMapViewModel @Inject constructor(
        private val getVehicles: GetVechicles,
        private val markerOptionsMapper: MarkerOptionsMapper,
        schedulers: victorpetrovski.com.domain.schedulers.Schedulers
) : ViewModel() {

    private val liveData: MutableLiveData<ViewResource<List<MarkerOptions>>> =
            MutableLiveData()

    fun getVehiclesLiveData() = liveData

    val DEBOUNCE_TIME = 500L

    var onMapMovedSubject = PublishSubject.create<GetVechicles.Params>()

    init {
        /**
         *  Use the debounce operator so we don't overload our API with calls every time we move the MAP
         * */
        onMapMovedSubject
                .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS,schedulers.computation)
                .observeOn(schedulers.observeOn)
                .subscribeOn(schedulers.subscribeOn)
                .flatMap {
                    liveData.postValue(ViewResource(ViewState.LOADING, null, null))
                    return@flatMap getVehicles.execute(ListVechiclesSubscriber(), it).onExceptionResumeNext { }
                }.subscribe({}, {})

    }


    fun onMapMoved(p1Lat: Double, p1Lon: Double,
                   p2Lat: Double, p2Lon: Double) {

        val params = GetVechicles.Params(p1Lat, p1Lon, p2Lat, p2Lon)

        onMapMovedSubject.onNext(params)
    }

    override fun onCleared() {
        getVehicles.dispose()
        onMapMovedSubject.onComplete()
        super.onCleared()
    }

    inner class ListVechiclesSubscriber : DisposableObserver<List<VehicleEntity>>() {

        override fun onNext(t: List<VehicleEntity>) {
            liveData.postValue(ViewResource(ViewState.SUCCESS,
                    t.map { markerOptionsMapper.mapFrom(it) }, null))
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {
            if(e is UnknownHostException)
                liveData.postValue(ViewResource(ViewState.ERROR, null,"Network not available"))
            else
                liveData.postValue(ViewResource(ViewState.ERROR, null,e.localizedMessage))
        }

    }
}