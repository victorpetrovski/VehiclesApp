package victorpetrovski.com.victortaxi.mapper

interface ViewMapper<in E, out V> {

    fun mapFrom(entity : E) : V
}
