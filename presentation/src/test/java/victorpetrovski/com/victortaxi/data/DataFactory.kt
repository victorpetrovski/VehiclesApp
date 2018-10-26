package victorpetrovski.com.victortaxi.data

import victorpetrovski.com.data.model.CoordinateData
import victorpetrovski.com.domain.model.VehicleEntity
import victorpetrovski.com.victortaxi.model.VehicleType
import victorpetrovski.com.victortaxi.model.VehicleView
import java.util.*

object DataFactory {

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

    fun randomVehicleType() : VehicleType{
        val rand = Random().nextInt(2)

        return if(rand < 1)
            VehicleType.TAXI
        else
            VehicleType.POOLING
    }

    fun makeVehicleView() = VehicleView(randomLong(), randomDouble(), randomDouble(), randomString(),randomFloat(),randomVehicleType())


    fun makeCoordinate() = CoordinateData(randomDouble(), randomDouble())


    fun makeVehicleEntity() =
            VehicleEntity(randomLong(), randomDouble(),
                    randomDouble(), randomString(), randomFloat())


    fun makeVehicleEntityList(count: Int): List<VehicleEntity> {
        val items = mutableListOf<VehicleEntity>()
        repeat(count) {
            items.add(makeVehicleEntity())
        }
        return items
    }

    fun makeVehicleViewList(count: Int): List<VehicleView> {
        val items = mutableListOf<VehicleView>()
        repeat(count) {
            items.add(makeVehicleView())
        }
        return items
    }



}