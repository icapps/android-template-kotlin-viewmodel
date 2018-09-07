package com.icapps.template.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.icapps.architecture.di.ViewModelFactory
import com.icapps.architecture.di.ViewModelKey
import com.icapps.template.viewmodel.ExampleViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author maartenvangiel
 * @version 1
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ExampleViewModel::class)
    internal abstract fun bindExampleViewModel(exampleViewModel: ExampleViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}