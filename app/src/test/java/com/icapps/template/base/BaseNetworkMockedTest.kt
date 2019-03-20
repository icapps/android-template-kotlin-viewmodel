package com.icapps.template.base

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.icapps.mockingj.junit.MockingJTestRule
import com.icapps.template.di.TestComponent
import com.icapps.template.di.TestNetworkModule
import com.icapps.template.di.TestRepositoryModule
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * @author maartenvangiel
 * @version 1
 */
@Suppress("unused")
@RunWith(MockitoJUnitRunner::class)
abstract class BaseNetworkMockedTest {

    protected lateinit var testComponent: TestComponent

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var mockingJRule = MockingJTestRule(mockAll = true, responseDirectory = "responses/success")

    @Before
    @CallSuper
    open fun setup() {
        testComponent = DaggerTestComponent.builder()
                .testNetworkModule(TestNetworkModule())
                .testRepositoryModule(TestRepositoryModule())
                .build()
        inject()
    }

    abstract fun inject()

}