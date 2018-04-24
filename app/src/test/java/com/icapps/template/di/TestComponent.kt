package com.icapps.template.di

import com.icapps.template.ExampleOverviewTest
import com.icapps.template.base.BaseTest
import com.icapps.template.viewmodel.ExampleViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * @author maartenvangiel
 * @version 1
 */
@Singleton
@Component(modules = [TestModule::class])
interface TestComponent {

    fun inject(baseTest: BaseTest)

    fun inject(exampleOverviewTest: ExampleOverviewTest)

    fun inject(exampleViewModel: ExampleViewModel)

}