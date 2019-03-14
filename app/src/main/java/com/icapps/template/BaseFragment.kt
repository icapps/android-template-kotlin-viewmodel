package com.icapps.template

import android.os.Bundle
import com.icapps.architecture.arch.BaseViewModel
import com.icapps.architecture.controller.ViewModelLifecycleController
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * @author maartenvangiel
 * @version 1
 */
abstract class BaseFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelLifecycleController: ViewModelLifecycleController

    protected inline fun <reified T : BaseViewModel> getOrCreateViewModel(savedInstanceState: Bundle? = null): T {
        return viewModelLifecycleController.getOrCreateViewModel(this, savedInstanceState)
    }

    protected inline fun <reified T : BaseViewModel> getOrCreateActivityViewModel(savedInstanceState: Bundle? = null): T {
        val activity = activity
                ?: throw IllegalStateException("Requesting viewModel for null activity")
        return viewModelLifecycleController.getOrCreateViewModel(activity, savedInstanceState)
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
