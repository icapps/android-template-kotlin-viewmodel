package com.icapps.template.di

import com.icapps.template.TemplateApplication
import com.icapps.template.generated.GeneratedAndroidInjectedModule
import com.icapps.template.generated.GeneratedViewModelModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * @author maartenvangiel
 * @version 1
 */
@Singleton
@Component(modules = [
    AppModule::class,
    NetworkModule::class,
    AndroidInjectionModule::class,
    ViewModelFactoryModule::class,
    GeneratedAndroidInjectedModule::class,
    GeneratedViewModelModule::class
])
interface AppComponent : AndroidInjector<TemplateApplication>