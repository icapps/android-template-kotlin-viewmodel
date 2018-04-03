package com.icapps.template

import android.arch.lifecycle.Observer
import com.icapps.template.base.BaseTest
import com.icapps.template.model.Beer
import com.icapps.template.model.arch.Resource
import com.icapps.template.model.arch.Status
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
class BeerOverviewTest : BaseTest() {

    private lateinit var viewModel: ExampleViewModel

    @Mock
    private lateinit var observer: Observer<Resource<List<Beer>>>

    override fun setup() {
        super.setup()
        viewModel = ExampleViewModel(beersRepository)
    }

    @Test
    fun testSuccessfulLoadAndRefresh() {
        viewModel.beers.observeForever(observer)
        viewModel.setSortAlphabetically(false)
        assert(viewModel.beers.value?.status == Status.LOADING)
        verify(observer, timeout(1000)).onChanged(argThat { status == Status.SUCCESS && data?.size == TestData.beers.size })

        viewModel.refresh()
        assert(viewModel.beers.value?.status == Status.LOADING)
        verify(observer, timeout(1000)).onChanged(argThat { status == Status.SUCCESS && data?.size == TestData.beers.size })
    }

    @Test
    fun testSort() {
        viewModel.beers.observeForever(observer)
        viewModel.setSortAlphabetically(true)

        assert(viewModel.beers.value?.status == Status.LOADING)
        verify(observer, timeout(1000)).onChanged(argThat {
            status == Status.SUCCESS && data?.sortedBy { it.name }?.map { it.id } == TestData.beers.sortedBy { it.name }.map { it.id }
        })

        viewModel.setSortAlphabetically(false)
        assert(viewModel.beers.value?.status == Status.LOADING)
        verify(observer, timeout(1000)).onChanged(argThat {
            status == Status.SUCCESS && data?.map { it.id } == TestData.beers.map { it.id }
        })
    }

}
