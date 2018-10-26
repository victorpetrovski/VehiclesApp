package victorpetrovski.com.data.mapper

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import victorpetrovski.com.data.data.VehicleDataFactory
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class ModelEntityMapperTest {

    private val modelEntityMapper = ModelEntityMapper()

    @Test
    fun testModelEntityMapper(){
        val remoteModel = VehicleDataFactory.makeVehicle()
        val entityModel = modelEntityMapper.mapFromModel(remoteModel)


        assertEquals(remoteModel.vehicleId,entityModel.id)
        assertEquals(remoteModel.cords.latitude,entityModel.latitude)
        assertEquals(remoteModel.cords.longitude,entityModel.longitude)
        assertEquals(remoteModel.type,entityModel.type)

    }
}