package com.icapps.template.di

import com.icapps.template.repository.DummyExampleRepository
import com.icapps.template.repository.ExampleRepository
import com.nhaarman.mockito_kotlin.spy
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author maartenvangiel
 * @version 1
 */
@Module
class TestModule {

    @Singleton
    @Provides
    fun provideExampleRepository(): ExampleRepository {
        return spy(DummyExampleRepository())
    }

}