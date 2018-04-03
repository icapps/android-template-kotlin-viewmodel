package com.icapps.template

import android.arch.lifecycle.Observer
import com.icapps.template.base.BaseTest
import com.icapps.template.model.Example
import com.icapps.template.model.arch.Resource
import com.icapps.template.model.arch.Status
import com.icapps.template.repository.TestData
import com.icapps.template.viewmodel.ExampleViewModel
import com.nhaarman.mockito_kotlin.argThat
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.timeout

/**
 * @author maartenvangiel
 * @version 1
 */
class ExampleOverviewTest : BaseTest() {

    private lateinit var viewModel: ExampleViewModel

    @Mock
    private lateinit var observer: Observer<Resource<List<Example>>>

    override fun setup() {
        super.setup()
        viewModel = ExampleViewModel(exampleRepository)
        viewModel.init()
    }

    @Test
    fun testSuccessfulLoad() {
        viewModel.examples.observeForever(observer)
        assert(viewModel.examples.value?.status == Status.LOADING)
        verify(observer, timeout(1000)).onChanged(argThat { status == Status.SUCCESS && data == TestData.examples })
    }

}
