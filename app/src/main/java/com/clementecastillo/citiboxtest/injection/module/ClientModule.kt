package com.clementecastillo.citiboxtest.injection.module

import android.content.Context
import com.clementecastillo.citiboxtest.BuildConfig
import com.clementecastillo.citiboxtest.client.TransactionRequestImpl
import com.clementecastillo.citiboxtest.client.api.ApiClientImpl
import com.clementecastillo.citiboxtest.client.api.PostsApiClient
import com.clementecastillo.citiboxtestcore.client.ApiClient
import com.clementecastillo.citiboxtestcore.transaction.TransactionRequest
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ClientModule {

    @Provides
    @Singleton
    fun transactionRequest(): TransactionRequest = TransactionRequestImpl()

    @Provides
    @Singleton
    fun gsonBase(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun cache(context: Context): Cache {
        val cacheDirName = "api_cache"
        val size = (10 * 1024 * 1024).toLong()
        val cacheDir = File(context.cacheDir, cacheDirName)
        return Cache(cacheDir, size)
    }

    @Provides
    @Singleton
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }

    @Provides
    @Singleton
    fun okHttpClient(logger: HttpLoggingInterceptor, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logger)
            .build()
    }

    @Provides
    @Singleton
    fun restApiClient(okHttpClient: OkHttpClient, gson: Gson): PostsApiClient {
        return Retrofit.Builder()
            .baseUrl(PostsApiClient.HOST)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(PostsApiClient::class.java)
    }

    @Provides
    @Singleton
    fun apiClient(
        postsApiClient: PostsApiClient
    ): ApiClient {
        return ApiClientImpl(postsApiClient)
    }
}