package com.icapps.template.di

import android.app.Application
import com.icapps.architecture.utils.async.ScalingThreadPoolExecutor
import com.icapps.niddler.core.AndroidNiddler
import com.icapps.niddler.core.Niddler
import com.icapps.niddler.interceptor.okhttp.NiddlerOkHttpInterceptor
import com.icapps.niddler.retrofit.NiddlerRetrofitCallInjector
import com.icapps.template.model.Constants
import com.icapps.template.service.ExampleService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author maartenvangiel
 * @version 1
 */
@Module
class NetworkModule(private val baseUrl: String) {

    private companion object {
        private const val NIDDLER_TRACE_SKIP = 5
        private const val NIDDLER_STACKTRACE_SIZE = 10
    }

    @Provides
    @Singleton
    fun provideNiddler(application: Application): Niddler {
        val niddler = AndroidNiddler.Builder()
                .setPort(0)
                .setNiddlerInformation(AndroidNiddler.fromApplication(application))
                .setMaxStackTraceSize(NIDDLER_STACKTRACE_SIZE)
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
    fun provideHttpExecutor(): Executor = ScalingThreadPoolExecutor(1, Constants.THREAD_POOL_MAX_THREAD_COUNT, Constants.THREAD_POOL_KEEP_ALIVE_SECONDS, TimeUnit.SECONDS)

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient, niddler: Niddler, executor: Executor): Retrofit {
        val builder = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .callbackExecutor(executor)
                .baseUrl(baseUrl)
                .client(okHttpClient)

        return NiddlerRetrofitCallInjector.inject(builder, niddler, okHttpClient, NIDDLER_TRACE_SKIP)
                .build()
    }

    @Provides
    @Singleton
    fun provideExampleService(retrofit: Retrofit): ExampleService {
        return retrofit.create(ExampleService::class.java)
    }

}