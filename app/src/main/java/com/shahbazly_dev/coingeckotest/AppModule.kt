package com.shahbazly_dev.coingeckotest

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.shahbazly_dev.coingeckotest.data.CoinGeckoService
import com.shahbazly_dev.coingeckotest.base.util.Constants.BASE_URL
import com.shahbazly_dev.coingeckotest.base.util.Constants.CONNECTION_TIMEOUT_SECONDS
import com.shahbazly_dev.coingeckotest.base.util.Constants.MEDIA_TYPE
import com.shahbazly_dev.coingeckotest.base.util.Constants.READ_TIMEOUT_SECONDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG)
            builder
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)

        return builder.build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(json: Json, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory(MEDIA_TYPE.toMediaType()))
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideCoinGeckoService(retrofit: Retrofit): CoinGeckoService =
        retrofit.create(CoinGeckoService::class.java)
}