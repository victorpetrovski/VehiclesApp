package victorpetrovski.com.data.remote

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import victorpetrovski.com.data.data.VehicleDataFactory
import victorpetrovski.com.data.mapper.ModelEntityMapper
import victorpetrovski.com.data.remote.api.MyTaxiService

@RunWith(MockitoJUnitRunner::class)
class VehiclesRemoteImplTest {


    @Mock
    lateinit var modelEntityMapper: ModelEntityMapper

    @Mock
    lateinit var taxiService: MyTaxiService

    lateinit var vehiclesRemoteImpl: VehiclesRemoteImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        vehiclesRemoteImpl = VehiclesRemoteImpl(taxiService, modelEntityMapper)
    }


    @Test
    fun testGetVehiclesComplete(){
        whenever(taxiService.searchRepositories(any(), any(), any(), any()))
                .thenReturn(Observable.just(VehicleDataFactory.makeVehicleResponseData(5)))

        val test = vehiclesRemoteImpl.getVehiclesInRange(any(), any(), any(), any()).test()

        test.assertComplete()
    }

    @Test
    fun testCorrectResponseData(){
        val p1Lat = VehicleDataFactory.randomDouble()
        val p1Long = VehicleDataFactory.randomDouble()
        val p2Lat = VehicleDataFactory.randomDouble()
        val p2Long = VehicleDataFactory.randomDouble()

        val count = 5

        val vehicleEntityList = VehicleDataFactory.makeVehicleEntityList(count)
        val vehicleDataList  = VehicleDataFactory.makeVehicleDataList(count)

        whenever(taxiService.searchRepositories(p1Lat,p1Long,p2Lat,p2Long))
                .thenReturn(Observable.just(VehicleDataFactory.makeVehicleResponseData(vehicleDataList)))

        repeat(5){
            whenever(modelEntityMapper.mapFromModel(vehicleDataList[it]))
                    .thenReturn(vehicleEntityList[it])
        }

        val test = vehiclesRemoteImpl.getVehiclesInRange(p1Lat,p1Long,p2Lat,p2Long).test()

        test.assertValue(vehicleEntityList)

    }
}