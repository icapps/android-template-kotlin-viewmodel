package com.icapps.template.di

import androidx.lifecycle.ViewModelProvider
import com.icapps.architecture.di.ViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * @author maartenvangiel
 * @version 1
 */
@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}