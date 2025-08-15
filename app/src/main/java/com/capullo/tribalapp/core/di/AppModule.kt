package com.capullo.tribalapp.core.di

import com.capullo.tribalapp.core.data.remote.ChuckNorrisRequestApiInt
import com.capullo.tribalapp.core.data.repo.ChuckRepoImpl
import com.capullo.tribalapp.core.domain.repo.ChuckRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


/* this is the module that provides the dependencies for the application.
    * It provides the OkHttpClient, Retrofit instance, and the chuckApi interface.
    * why singleton? because we want to have a single instance of
    * these classes throughout the application.
    * This module is installed in the SingletonComponent, which means that
    * the provided dependencies will be available for the entire application lifecycle.
    * <3 Vmoreno
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.chucknorris.io/"

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder().addInterceptor(logger).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ChuckNorrisRequestApiInt =
        retrofit.create(ChuckNorrisRequestApiInt::class.java)

    @Provides
    @Singleton
    fun provideCategoryRepository(api: ChuckNorrisRequestApiInt): ChuckRepo =
        ChuckRepoImpl(api)
}
