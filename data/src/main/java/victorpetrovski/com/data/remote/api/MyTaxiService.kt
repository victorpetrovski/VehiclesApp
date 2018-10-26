package victorpetrovski.com.data.remote.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import victorpetrovski.com.data.model.VehicleResponseData

interface MyTaxiService {

    @GET(".")
    fun searchRepositories(@Query("p1Lat") latitude1 : Double,
                           @Query("p1Lon") longitude1: Double,
                           @Query("p2Lat") latitude2: Double,
                           @Query("p2Lon") longitude2: Double)
            : Observable<VehicleResponseData>

}