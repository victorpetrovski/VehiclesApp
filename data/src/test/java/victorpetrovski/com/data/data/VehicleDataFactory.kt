package victorpetrovski.com.data.data

import victorpetrovski.com.data.model.CoordinateData
import victorpetrovski.com.data.model.VehicleData
import victorpetrovski.com.data.model.VehicleResponseData
import victorpetrovski.com.domain.model.VehicleEntity
import java.util.*

object VehicleDataFactory {

    fun randomDouble(): Double {
        return Random().nextDouble()
    }

    fun randomLong(): Long {
        return Random().nextLong()
    }

    fun randomFloat(): Float {
        return Random().nextFloat()
    }

    fun randomString(): String {
        return java.util.UUID.randomUUID().toString()
    }

    fun makeVehicle() = VehicleData(makeCoordinate(), randomLong(), randomString(), randomFloat())


    fun makeCoordinate() = CoordinateData(randomDouble(), randomDouble())

    fun makeVehicleDataList(count: Int): List<VehicleData> {
        val items = mutableListOf<VehicleData>()
        repeat(count) {
            items.add(makeVehicle())
        }
        return items
    }

    fun makeVehicleEntity() =
            VehicleEntity(VehicleDataFactory.randomLong(), VehicleDataFactory.randomDouble(),
                    VehicleDataFactory.randomDouble(), VehicleDataFactory.randomString(), randomFloat())


    fun makeVehicleEntityList(count: Int): List<VehicleEntity> {
        val items = mutableListOf<VehicleEntity>()
        repeat(count) {
            items.add(makeVehicleEntity())
        }
        return items
    }


    fun makeVehicleResponseData(count: Int) : VehicleResponseData {
        val items = mutableListOf<VehicleData>()

        repeat(count) {
            items.add(makeVehicle())
        }

        return VehicleResponseData(items)
    }


    fun makeVehicleResponseData(items: List<VehicleData>) : VehicleResponseData {
        return VehicleResponseData(items)
    }
}