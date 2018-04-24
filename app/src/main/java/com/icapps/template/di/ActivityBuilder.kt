package com.icapps.template.di

import com.icapps.template.activity.MainActivity
import com.icapps.template.activity.TestActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author maartenvangiel
 * @version 1
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindTestActivity(): TestActivity

}