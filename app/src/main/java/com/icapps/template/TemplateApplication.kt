package com.icapps.template

import com.icapps.template.di.AppModule
import com.icapps.template.di.DaggerAppComponent
import com.icapps.template.di.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.HasActivityInjector

/**
 * @author maartenvangiel
 * @version 1
 */
class TemplateApplication : DaggerApplication(), HasActivityInjector {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appModule = AppModule(this)
        val networkModule = NetworkModule(getString(R.string.base_url))

        return DaggerAppComponent.builder()
                .appModule(appModule)
                .networkModule(networkModule)
                .build()
    }

}