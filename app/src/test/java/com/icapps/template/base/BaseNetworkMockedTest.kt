package com.icapps.template.base

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.support.annotation.CallSuper
import com.icapps.mockingj.MockingJServer
import com.icapps.template.TestEnvironment
import com.icapps.template.di.DaggerTestComponent
import com.icapps.template.di.TestComponent
import com.icapps.template.di.TestNetworkModule
import com.icapps.template.di.TestRepositoryModule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
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
    private lateinit var mockServer: MockingJServer

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    @CallSuper
    open fun setup() {
        mockServer = MockingJServer()
        TestEnvironment.baseUrl = mockServer.start()

        testComponent = DaggerTestComponent.builder()
                .testNetworkModule(TestNetworkModule())
                .testRepositoryModule(TestRepositoryModule())
                .build()
        inject()
    }

    @After
    open fun tearDown() {
        mockServer.stop()
    }

    abstract fun inject()

}