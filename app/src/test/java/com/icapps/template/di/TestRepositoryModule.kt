package com.icapps.template.di

import com.icapps.template.repository.ExampleRepository
import com.icapps.template.service.ExampleService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author maartenvangiel
 * @version 1
 */
@Module
class TestRepositoryModule {

    @Singleton
    @Provides
    fun provideExampleRepository(exampleService: ExampleService): ExampleRepository {
        return ExampleRepository(exampleService)
    }

}