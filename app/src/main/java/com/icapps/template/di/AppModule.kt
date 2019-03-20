package com.icapps.template.di

import android.app.Application
import android.content.Context
import com.icapps.architecture.controller.ViewModelLifecycleController
import com.icapps.architecture.di.ViewModelFactory
import com.icapps.template.TemplateApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author maartenvangiel
 * @version 1
 */
@Module
class AppModule(private val app: TemplateApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = app

    @Provides
    fun provideViewModelLifecycleController(factory: ViewModelFactory): ViewModelLifecycleController {
        return ViewModelLifecycleController(factory)
    }

}