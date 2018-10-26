package victorpetrovski.com.data

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
import victorpetrovski.com.data.model.VehicleData
import victorpetrovski.com.data.repository.VehiclesDataRepository
import victorpetrovski.com.data.repository.gateway.VehiclesRemote
import victorpetrovski.com.domain.model.VehicleEntity

@RunWith(MockitoJUnitRunner::class)
class VehiclesDataRepositoryTest {

    private lateinit var vehiclesDataRepository : VehiclesDataRepository

    @Mock
    lateinit var vehiclesRemote: VehiclesRemote

    @Mock
    lateinit var modelEntityMapper: ModelEntityMapper

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        vehiclesDataRepository = VehiclesDataRepository(vehiclesRemote)
    }


    @Test
    fun getVehiclesComplete(){
        whenever(vehiclesRemote.getVehiclesInRange(any(), any(), any(), any()))
                .thenReturn(Observable.just(VehicleDataFactory.makeVehicleEntityList(5)))

        val test = vehiclesDataRepository.getAllVehiclesInRange(any(), any(), any(), any()).test()

        test.assertComplete()
    }

    @Test
    fun testVehiclesReturnedData(){

        val p1Lat = VehicleDataFactory.randomDouble()
        val p1Long = VehicleDataFactory.randomDouble()
        val p2Lat = VehicleDataFactory.randomDouble()
        val p2Long = VehicleDataFactory.randomDouble()

        val returnList = VehicleDataFactory.makeVehicleEntityList(5)

        whenever(vehiclesRemote.getVehiclesInRange(p1Lat,p1Long,p2Lat,p2Long))
                .thenReturn(Observable.just(returnList))

        val test = vehiclesDataRepository.getAllVehiclesInRange(p1Lat, p1Long, p2Lat, p2Long).test()

        test.assertValue(returnList)
    }

    fun stubMapper(model : VehicleData, entity: VehicleEntity){
        whenever(modelEntityMapper.mapFromModel(model)).thenReturn(entity)
    }





}