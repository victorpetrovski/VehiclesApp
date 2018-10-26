package victorpetrovski.com.victortaxi.vehiclesMap

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.maps.model.MarkerOptions
import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.DisposableObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import victorpetrovski.com.domain.model.VehicleEntity
import victorpetrovski.com.domain.usecase.listVehicels.GetVechicles
import victorpetrovski.com.victortaxi.data.DataFactory
import victorpetrovski.com.victortaxi.executor.TestSchedulers
import victorpetrovski.com.victortaxi.features.vehiclesMap.VehiclesMapViewModel
import victorpetrovski.com.victortaxi.mapper.MarkerOptionsMapper
import victorpetrovski.com.victortaxi.state.ViewState
import java.io.IOException
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class VehiclesMapViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    val markerOptionsMapper = mock<MarkerOptionsMapper>()

    val testSchedulers = TestSchedulers()

    lateinit var vehiclesMapViewModel: VehiclesMapViewModel

    val getVehicles = mock<GetVechicles>()

    @Before
    fun setup() {
        vehiclesMapViewModel = VehiclesMapViewModel(getVehicles, markerOptionsMapper, testSchedulers)
    }

    @Test
    fun verifyGetVehiclesOnMapMoveExcutesUseCase() {
        vehiclesMapViewModel.onMapMoved(DataFactory.randomDouble(), DataFactory.randomDouble(), DataFactory.randomDouble(), DataFactory.randomDouble())

        verify(getVehicles, times(1)).execute(any(), any())
    }


    @Captor
    val captor = argumentCaptor<DisposableObserver<List<VehicleEntity>>>()

    @Test
    fun verifyGetVehiclesForMapReturnsSuccess() {
        val vehicleEntityList = DataFactory.makeVehicleEntityList(2)
        val markerOptionsList = listOf<MarkerOptions>(MarkerOptions(),MarkerOptions())

        stubVehicleViewToEntityMapper(markerOptionsList[0], vehicleEntityList[0])
        stubVehicleViewToEntityMapper(markerOptionsList[1], vehicleEntityList[1])

        vehiclesMapViewModel.onMapMoved(DataFactory.randomDouble(), DataFactory.randomDouble(), DataFactory.randomDouble(), DataFactory.randomDouble())

        verify(getVehicles).execute(captor.capture(), any())
        captor.firstValue.onNext(vehicleEntityList)

        assertEquals(ViewState.SUCCESS, vehiclesMapViewModel.getVehiclesLiveData().value?.status)
    }

    @Test
    fun verifyGetVehiclesForMapResponseData() {
        val vehicleEntityList = DataFactory.makeVehicleEntityList(2)
        val markerOptionsList = listOf(MarkerOptions(),MarkerOptions())

        stubVehicleViewToEntityMapper(markerOptionsList[0], vehicleEntityList[0])
        stubVehicleViewToEntityMapper(markerOptionsList[1], vehicleEntityList[1])

        vehiclesMapViewModel.onMapMoved(DataFactory.randomDouble(), DataFactory.randomDouble(), DataFactory.randomDouble(), DataFactory.randomDouble())

        verify(getVehicles).execute(captor.capture(), any())
        captor.firstValue.onNext(vehicleEntityList)

        assertEquals(markerOptionsList, vehiclesMapViewModel.getVehiclesLiveData().value?.data)

    }

    @Test
    fun verifyGetVehiclesOnError() {
        val vehicleEntityList = DataFactory.makeVehicleEntityList(2)
        val markerOptionsList = listOf<MarkerOptions>(MarkerOptions(),MarkerOptions())

        stubVehicleViewToEntityMapper(markerOptionsList[0], vehicleEntityList[0])

        vehiclesMapViewModel.onMapMoved(DataFactory.randomDouble(), DataFactory.randomDouble(), DataFactory.randomDouble(), DataFactory.randomDouble())

        verify(getVehicles).execute(captor.capture(), any())
        captor.firstValue.onError(IOException())

        assertEquals(ViewState.ERROR, vehiclesMapViewModel.getVehiclesLiveData().value?.status)
    }

    fun stubVehicleViewToEntityMapper(markerOptions: MarkerOptions, vehicleEntity: VehicleEntity) {
        whenever(markerOptionsMapper.mapFrom(vehicleEntity)).thenReturn(markerOptions)
    }
}