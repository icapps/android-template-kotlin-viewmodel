package com.icapps.template.activity

import android.os.Bundle
import com.icapps.architecture.arch.BaseViewModel
import com.icapps.architecture.controller.ViewModelLifecycleController
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * @author maartenvangiel
 * @version 1
 */
abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelLifecycleController: ViewModelLifecycleController

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

}
