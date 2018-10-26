package victorpetrovski.com.domain.data

import victorpetrovski.com.domain.model.VehicleEntity
import java.util.*

object VehicleDataFactory {

    fun randomDouble(): Double {
        return Random().nextDouble()
    }

    fun randomLong(): Long {
        return Random().nextLong()
    }

    fun randomString(): String {
        return java.util.UUID.randomUUID().toString()
    }

    fun randomFloat(): Float {
        return Random().nextFloat()
    }

    fun makeVehicle() = VehicleEntity(randomLong(), randomDouble(), randomDouble(), randomString(), randomFloat())


    fun makeVehicleList(count: Int): List<VehicleEntity> {
        val items = mutableListOf<VehicleEntity>()
        repeat(count) {
            items.add(makeVehicle())
        }
        return items
    }
}