package com.issell.benefits.util

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.concurrent.TimeUnit

object ApiFactory {
    private var mClient: OkHttpClient? = null
    private var mGsonConverter: GsonConverterFactory? = null
    private val mLocaleInterceptor:Interceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .header("Accept-Language", Locale.getDefault().language )
                .build()
            Log.i("test", "Lang : ${Locale.getDefault().language}")
//            if (!"/posts".contains(originalRequest.url.toString(), false)) {
//                return chain.proceed(originalRequest)
//            }
            return chain.proceed(newRequest)
        }
    }


    /**
     * Don't forget to remove Interceptors (or change Logging Level to NONE)
     * in production! Otherwise people will be able to see your request and response on Log Cat.
     */
    val client: OkHttpClient
        @Throws(NoSuchAlgorithmException::class, KeyManagementException::class)
        get() {
            Log.i("test", "11111")
            if (mClient == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                Log.i("test", "22222")
                val httpBuilder = OkHttpClient.Builder()
                httpBuilder
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)  /// show all JSON in logCat
                    .addInterceptor(mLocaleInterceptor)
                mClient = httpBuilder.build()

            }
            return mClient!!
        }


    val gsonConverter: GsonConverterFactory
        get() {
            if(mGsonConverter == null){
                mGsonConverter = GsonConverterFactory
                    .create(GsonBuilder()
                        .setLenient()
                        .disableHtmlEscaping()
                        .create())
            }
            return mGsonConverter!!
        }
}