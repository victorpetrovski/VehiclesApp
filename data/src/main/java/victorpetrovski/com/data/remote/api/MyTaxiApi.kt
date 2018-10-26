package victorpetrovski.com.data.remote.api

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MyTaxiApi(private val baseUrl: String, private val isDebug: Boolean) : MyTaxiService {

    private lateinit var myTaxiService: MyTaxiService

    init {
        makeTaxiService()
    }

    open fun makeTaxiService(): MyTaxiService {
        val okHttpClient = makeOkHttpClient(
                makeLoggingInterceptor((isDebug)))
        myTaxiService = makeTaxiService(okHttpClient, Gson())
        return myTaxiService
    }

    private fun makeTaxiService(okHttpClient: OkHttpClient, gson: Gson): MyTaxiService {
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        return retrofit.create(MyTaxiService::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

    override fun searchRepositories(latitude1: Double, longitude1: Double, latitude2: Double, longitude2: Double) =
            myTaxiService.searchRepositories(latitude1, longitude1, latitude2, longitude2)

}