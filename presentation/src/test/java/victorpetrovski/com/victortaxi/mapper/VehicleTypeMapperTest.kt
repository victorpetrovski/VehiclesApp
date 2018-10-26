package victorpetrovski.com.victortaxi.mapper

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import victorpetrovski.com.victortaxi.model.VehicleType
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class VehicleTypeMapperTest {

    var vehicleTypeMapper = VehicleTypeMapper()

    @Test
    fun testVehicleTypeMapper(){
        assertEquals(vehicleTypeMapper.mapFrom("taxi"),VehicleType.TAXI)
        assertEquals(vehicleTypeMapper.mapFrom("pooling"),VehicleType.POOLING)

        //Test that ignores case
        assertEquals(vehicleTypeMapper.mapFrom("TAXI"),VehicleType.TAXI)
        assertEquals(vehicleTypeMapper.mapFrom("POOling"),VehicleType.POOLING)
    }
}