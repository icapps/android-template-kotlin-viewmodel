package com.icapps.template.di

import com.icapps.template.TemplateApplication
import com.icapps.template.viewmodel.ExampleViewModel
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author maartenvangiel
 * @version 1
 */
@Singleton
@Component(modules = [AppModule::class, AndroidSupportInjectionModule::class, ActivityBuilder::class, NetworkModule::class, RepositoryModule::class, ViewModelModule::class])
interface AppComponent : AndroidInjector<TemplateApplication> {

    fun inject(exampleViewModel: ExampleViewModel)

}