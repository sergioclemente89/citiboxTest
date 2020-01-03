package com.clementecastillo.citiboxtest.injection.module

import android.content.Context
import com.clementecastillo.citiboxtest.BuildConfig
import com.clementecastillo.citiboxtest.client.TransactionRequestImpl
import com.clementecastillo.citiboxtest.client.api.ApiClientImpl
import com.clementecastillo.citiboxtest.client.api.MockApiClient
import com.clementecastillo.citiboxtest.client.api.PostsApiClient
import com.clementecastillo.citiboxtestcore.client.ApiClient
import com.clementecastillo.citiboxtestcore.transaction.TransactionRequest
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.*
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
    fun connectionSpecs(): List<ConnectionSpec> {
        return listOf(
            ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .supportsTlsExtensions(true)
                .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                .cipherSuites(
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA,
                    CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA,
                    CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
                    CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
                    CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA
                ).build()
        )
    }

    @Provides
    @Singleton
    fun okHttpClient(logger: HttpLoggingInterceptor, cache: Cache, connectionSpecs: List<ConnectionSpec>): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logger)
            .connectionSpecs(connectionSpecs) // Fix TLS error for Pre-Lollipop
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

    @Provides
    @Singleton
    fun mockApiClient(gson: Gson): MockApiClient {
        return MockApiClient(gson)
    }
}