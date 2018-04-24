package com.icapps.template.base

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.support.annotation.CallSuper
import com.icapps.template.di.DaggerTestComponent
import com.icapps.template.di.TestComponent
import com.icapps.template.di.TestModule
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

/**
 * @author maartenvangiel
 * @version 1
 */
@Suppress("unused")
@RunWith(MockitoJUnitRunner::class)
abstract class BaseTest {

    protected lateinit var testComponent: TestComponent

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    @CallSuper
    open fun setup() {
        testComponent = DaggerTestComponent.builder()
                .testModule(TestModule())
                .build()
        inject()
    }

    abstract fun inject()

}