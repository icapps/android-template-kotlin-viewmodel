package com.icapps.template.di

import android.app.Application
import com.icapps.niddler.core.AndroidNiddler
import com.icapps.niddler.core.Niddler
import com.icapps.niddler.interceptor.okhttp.NiddlerOkHttpInterceptor
import com.icapps.template.service.ExampleService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton


/**
 * @author maartenvangiel
 * @version 1
 */
@Module
class NetworkModule(private val baseUrl: String) {

    @Provides
    @Singleton
    fun provideNiddler(application: Application): Niddler {
        val niddler = AndroidNiddler.Builder()
                .setPort(0)
                .setNiddlerInformation(AndroidNiddler.fromApplication(application))
                .build()
        niddler.attachToApplication(application)
        return niddler
    }

    @Provides
    @Singleton
    @Named("NiddlerInterceptor")
    fun provideNiddlerInterceptor(niddler: Niddler): Interceptor {
        return NiddlerOkHttpInterceptor(niddler, "NiddlerInterceptor")
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(@Named("NiddlerInterceptor") niddlerInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(niddlerInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun provideExampleService(retrofit: Retrofit): ExampleService {
        return retrofit.create(ExampleService::class.java)
    }

}