package com.icapps.template.activity

import android.os.Bundle
import com.icapps.architecture.arch.BaseViewModel
import com.icapps.architecture.controller.ViewModelLifecycleController
import com.icapps.template.di.ResourcesWrapper
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * @author maartenvangiel
 * @version 1
 */
abstract class BaseActivity : DaggerAppCompatActivity(), ResourcesWrapper {

    @Inject
    lateinit var viewModelLifecycleController: ViewModelLifecycleController

    protected inline val resourcesWrapper: ResourcesWrapper
        get() = this

    protected inline fun <reified T : BaseViewModel> getOrCreateViewModel(savedInstanceState: Bundle? = null): T {
        return viewModelLifecycleController.getOrCreateViewModel(this, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModelLifecycleController.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        viewModelLifecycleController.onDestroy()
        super.onDestroy()
    }

    override fun getInt(resId: Int): Int {
        return resources.getInteger(resId)
    }

}
