package com.icapps.template

import android.arch.lifecycle.Observer
import com.icapps.mockingj.junit.MockResponses
import com.icapps.template.base.BaseNetworkMockedTest
import com.icapps.template.model.Example
import com.icapps.template.model.arch.Resource
import com.icapps.template.model.arch.Status
import com.icapps.template.viewmodel.ExampleViewModel
import com.nhaarman.mockito_kotlin.argThat
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.timeout
import javax.inject.Inject

/**
 * @author maartenvangiel
 * @version 1
 */
class ExampleOverviewTest : BaseNetworkMockedTest() {

    @Mock
    private lateinit var observer: Observer<Resource<List<Example>>>

    @Inject
    lateinit var viewModel: ExampleViewModel

    override fun inject() {
        testComponent.inject(this)
    }

    @Test
    fun testSuccessfulLoad() {
        viewModel.init()
        viewModel.examples.observeForever(observer)

        assert(viewModel.examples.value?.status == Status.LOADING)

        verify(observer, timeout(1000)).onChanged(argThat {
            status == Status.SUCCESS &&
                    data?.get(0)?.id == 1L &&
                    data?.get(0)?.value == "First item" &&
                    data?.get(1)?.id == 2L &&
                    data?.get(1)?.value == "Second item" &&
                    data?.size == 2
        })
    }

    @Test
    @MockResponses(overrideResponseDirectory = "responses/failure")
    fun testFailureResponse() {
        viewModel.init()
        viewModel.examples.observeForever(observer)

        assert(viewModel.examples.value?.status == Status.LOADING)

        verify(observer, timeout(1000)).onChanged(argThat {
            status == Status.ERROR
        })
    }

}