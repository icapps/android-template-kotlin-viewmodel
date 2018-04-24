package com.icapps.template.di

import com.icapps.template.repository.DefaultExampleRepository
import com.icapps.template.repository.ExampleRepository
import com.icapps.template.webservice.ExampleService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author maartenvangiel
 * @version 1
 */
@Module
open class RepositoryModule {

    @Provides
    @Singleton
    open fun provideExampleRepository(exampleService: ExampleService): ExampleRepository {
        return DefaultExampleRepository(exampleService)
    }

}