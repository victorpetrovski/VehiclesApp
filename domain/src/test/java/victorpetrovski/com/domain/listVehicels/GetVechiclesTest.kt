package victorpetrovski.com.domain.listVehicels

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import victorpetrovski.com.domain.MyTestSchedulers
import victorpetrovski.com.domain.data.VehicleDataFactory
import victorpetrovski.com.domain.gateway.VehicleRepository
import victorpetrovski.com.domain.usecase.listVehicels.GetVechicles

@RunWith(MockitoJUnitRunner::class)
class GetVechiclesTest {

    @Mock
    private lateinit var vehicleRepository: VehicleRepository

    private val  testScheduler = MyTestSchedulers()

    private lateinit var getVechicles: GetVechicles

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        getVechicles = GetVechicles(vehicleRepository,testScheduler)
    }

    @Test
    fun searchVehiclesComplete(){
        val vehiclesList = VehicleDataFactory.makeVehicleList(5)
        whenever(vehicleRepository.getAllVehiclesInRange(any(), any(), any(), any())).thenReturn(Observable.just(vehiclesList))

        val randomDouble = VehicleDataFactory.randomDouble()

        val testObservable = getVechicles.buildUseCaseObservable(GetVechicles.Params(randomDouble,randomDouble,randomDouble,randomDouble)).test()

        testObservable
                .assertValue(vehiclesList)
                .assertValue { results -> results.size == 5 }
                .assertComplete()
    }

    @Test
    fun verifyVehiclesRepositoryCalled(){
        val vehiclesList = VehicleDataFactory.makeVehicleList(5)
        whenever(vehicleRepository.getAllVehiclesInRange(any(), any(), any(), any())).thenReturn(Observable.just(vehiclesList))

        val p1Lat = VehicleDataFactory.randomDouble()
        val p1Long = VehicleDataFactory.randomDouble()
        val p2Lat = VehicleDataFactory.randomDouble()
        val p2Long = VehicleDataFactory.randomDouble()

        getVechicles.buildUseCaseObservable(GetVechicles.Params(p1Lat,p1Long,p2Lat,p2Long))

        verify(vehicleRepository).getAllVehiclesInRange(p1Lat,p1Long,p2Lat,p2Long )
    }


    @Test
    fun verifyListVehiclesProperlyReturned(){
        val vehiclesList = VehicleDataFactory.makeVehicleList(5)

        whenever(vehicleRepository.getAllVehiclesInRange(any(), any(), any(), any())).thenReturn(Observable.just(vehiclesList))

        val randomDouble = VehicleDataFactory.randomDouble()

        val testObservable = getVechicles.buildUseCaseObservable(GetVechicles.Params(randomDouble,randomDouble,randomDouble,randomDouble)).test()

        testObservable.assertValue(vehiclesList)
    }

    @Test ( expected = IllegalArgumentException::class)
    fun verifyExceptionThrownWhenNullParam(){
        getVechicles.buildUseCaseObservable().test()
    }
}