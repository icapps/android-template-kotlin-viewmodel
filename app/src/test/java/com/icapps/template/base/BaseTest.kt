package com.icapps.template.base

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.icapps.template.repository.DummyExampleRepository
import com.icapps.template.repository.ExampleRepository
import com.nhaarman.mockito_kotlin.spy
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * @author maartenvangiel
 * @version 1
 */
@RunWith(MockitoJUnitRunner::class)
abstract class BaseTest {

    protected lateinit var exampleRepository: ExampleRepository

    @Suppress("unused")
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    open fun setup() {
        exampleRepository = spy(DummyExampleRepository())
    }

}