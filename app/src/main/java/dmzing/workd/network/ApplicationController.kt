package dmzing.workd.network

import android.app.Application
import dmzing.workd.util.NullOnEmptyConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient



/**
 * Created by VictoryWoo
 */
class ApplicationController : Application(){


    lateinit var networkService : NetworkService
    private val baseUrl = "http://52.79.50.98:8080/"
    companion object {
        lateinit var instance : ApplicationController
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        buildNetwork()
    }

    fun buildNetwork(){



        val builder = Retrofit.Builder()
        val retrofit = builder
            .baseUrl(baseUrl)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()



        networkService = retrofit.create(NetworkService::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        return builder.build()
    }
}