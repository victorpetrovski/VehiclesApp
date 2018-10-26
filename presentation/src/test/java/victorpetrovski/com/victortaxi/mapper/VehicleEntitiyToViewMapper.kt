package victorpetrovski.com.victortaxi.mapper

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import victorpetrovski.com.victortaxi.data.DataFactory
import victorpetrovski.com.victortaxi.gateway.LatLongConverter
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class VehicleEntitiyToViewMapper {


    @Mock
    lateinit var vehicleTypeMapper: VehicleTypeMapper

    @Mock
    lateinit var latLongConverter: LatLongConverter

    lateinit var mapper: EntityMapper

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mapper = EntityMapper(vehicleTypeMapper, latLongConverter)

    }

    @Test
    fun mapToView() {

        val mockedAddress = DataFactory.randomString()
        val vehicleType = DataFactory.randomVehicleType()

        whenever(latLongConverter.extractAddressFromCoordinates(any(), any())).thenReturn(mockedAddress)
        whenever(vehicleTypeMapper.mapFrom(any())).thenReturn(vehicleType)

        val entity = DataFactory.makeVehicleEntity()
        val view = mapper.mapFrom(entity)

        assertEquals(entity.id,view.id)
        assertEquals(entity.latitude,view.latitude)
        assertEquals(entity.longitude,view.longitude)
        assertEquals(entity.rotation,view.heading)
        assertEquals(vehicleType,view.type)
        assertEquals(mockedAddress,view.locationAddress)


    }

}
