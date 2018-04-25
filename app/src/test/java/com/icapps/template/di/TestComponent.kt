package com.icapps.template.di

import com.icapps.template.ExampleOverviewTest
import com.icapps.template.base.BaseNetworkMockedTest
import com.icapps.template.viewmodel.ExampleViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * @author maartenvangiel
 * @version 1
 */
@Singleton
@Component(modules = [TestNetworkModule::class, TestRepositoryModule::class])
interface TestComponent {

    fun inject(baseNetworkMockedTest: BaseNetworkMockedTest)

    fun inject(exampleOverviewTest: ExampleOverviewTest)

    fun inject(exampleViewModel: ExampleViewModel)

}