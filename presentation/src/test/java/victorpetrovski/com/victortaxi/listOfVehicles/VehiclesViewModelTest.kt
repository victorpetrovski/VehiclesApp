package victorpetrovski.com.victortaxi.listOfVehicles

import android.accounts.NetworkErrorException
import android.arch.core.executor.testing.InstantTaskExecutorRule
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
import victorpetrovski.com.victortaxi.features.listOfVehicles.VehiclesViewModel
import victorpetrovski.com.victortaxi.features.vehiclesMap.VehiclesMapViewModel
import victorpetrovski.com.victortaxi.mapper.EntityMapper
import victorpetrovski.com.victortaxi.mapper.MarkerOptionsMapper
import victorpetrovski.com.victortaxi.model.VehicleView
import victorpetrovski.com.victortaxi.state.ViewState
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class VehiclesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    var getVehicles = mock<GetVechicles>()

    var entityMapper = mock<EntityMapper>()

    var schedulers = TestSchedulers()

    lateinit var vehiclesViewModel: VehiclesViewModel

    @Before
    fun setup() {
        vehiclesViewModel = VehiclesViewModel(getVehicles, entityMapper, schedulers)
    }


    @Test
    fun verifyGetVehiclesExcutesUseCase() {
        vehiclesViewModel.loadVehiclesList()

        verify(getVehicles, times(1)).execute(any(), any())
    }

    @Captor
    val captor = argumentCaptor<DisposableObserver<List<VehicleEntity>>>()

    @Test
    fun verifyGetVehiclesReturnsSuccess() {
        val vehicleEntityList = DataFactory.makeVehicleEntityList(2)
        val vehicleViewList = DataFactory.makeVehicleViewList(2)

        stubVehicleViewToEntityMapper(vehicleViewList[0], vehicleEntityList[0])
        stubVehicleViewToEntityMapper(vehicleViewList[1], vehicleEntityList[1])

        vehiclesViewModel.loadVehiclesList()

        verify(getVehicles).execute(captor.capture(), any())
        captor.firstValue.onNext(vehicleEntityList)

        assertEquals(ViewState.SUCCESS, vehiclesViewModel.getVehiclesLiveData().value?.status)
    }

    @Test
    fun verifyGetVehiclesResponseData() {
        val vehicleEntityList = DataFactory.makeVehicleEntityList(2)
        val vehicleViewList = DataFactory.makeVehicleViewList(2)

        stubVehicleViewToEntityMapper(vehicleViewList[0], vehicleEntityList[0])
        stubVehicleViewToEntityMapper(vehicleViewList[1], vehicleEntityList[1])

        vehiclesViewModel.loadVehiclesList()

        verify(getVehicles).execute(captor.capture(), any())
        captor.firstValue.onNext(vehicleEntityList)

        assertEquals(vehicleViewList, vehiclesViewModel.getVehiclesLiveData().value?.data)
    }

    @Test
    fun verifyGetVehiclesOnError() {
        val vehicleEntityList = DataFactory.makeVehicleEntityList(2)
        val vehicleViewList = DataFactory.makeVehicleViewList(2)

        stubVehicleViewToEntityMapper(vehicleViewList[0], vehicleEntityList[0])
        stubVehicleViewToEntityMapper(vehicleViewList[1], vehicleEntityList[1])

        vehiclesViewModel.loadVehiclesList()

        verify(getVehicles).execute(captor.capture(), any())
        captor.firstValue.onError(NetworkErrorException())

        assertEquals(ViewState.ERROR, vehiclesViewModel.getVehiclesLiveData().value?.status)
    }

    fun stubVehicleViewToEntityMapper(vehicleView: VehicleView, vehicleEntity: VehicleEntity) {
        whenever(entityMapper.mapFrom(vehicleEntity)).thenReturn(vehicleView)
    }


}
