package com.icapps.template

import android.arch.lifecycle.Observer
import com.icapps.template.base.BaseTest
import com.icapps.template.model.Beer
import com.icapps.template.model.arch.Resource
import com.icapps.template.model.arch.Status
import com.icapps.template.repository.TestData
import com.icapps.template.viewmodel.BeerDetailViewModel
import com.nhaarman.mockito_kotlin.argThat
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.timeout

/**
 * @author maartenvangiel
 * @version 1
 */
class BeerDetailTest : BaseTest() {

    private lateinit var viewModel: BeerDetailViewModel

    @Mock
    private lateinit var observer: Observer<Resource<Beer?>>

    override fun setup() {
        super.setup()
        viewModel = BeerDetailViewModel(beersRepository)
    }

    @Test
    fun testSuccessfulLoadAndRefresh() {
        viewModel.beer.observeForever(observer)
        viewModel.loadBeer(TestData.beers.first().id)

        assert(viewModel.beer.value?.status == Status.LOADING)
        verify(observer, timeout(1000)).onChanged(argThat { status == Status.SUCCESS && data?.id == TestData.beers.first().id })

        viewModel.loadBeer(TestData.beers.first().id)
        assert(viewModel.beer.value?.status == Status.LOADING)
        verify(observer, timeout(1000)).onChanged(argThat { status == Status.SUCCESS && data?.id == TestData.beers.first().id })
    }

    @Test
    fun testSuccessfulFailedLoad() {
        viewModel.beer.observeForever(observer)
        viewModel.loadBeer(123456) // nonexistent Id

        assert(viewModel.beer.value?.status == Status.LOADING)
        verify(observer, timeout(1000)).onChanged(argThat { status == Status.ERROR })
    }

}
