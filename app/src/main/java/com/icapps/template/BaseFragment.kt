package com.icapps.template

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.icapps.template.arch.BaseViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * @author maartenvangiel
 * @version 1
 */
abstract class BaseFragment : DaggerFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val viewModels = mutableMapOf<KClass<*>, BaseViewModel>()

    protected inline fun <reified T : BaseViewModel> getOrCreateViewModel(savedInstanceState: Bundle? = null): T {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)[T::class.java]

        savedInstanceState?.let {
            viewModel.restoreInstanceState(it)
        }

        viewModels[T::class] = viewModel
        return viewModel
    }

    protected inline fun <reified T : BaseViewModel> getOrCreateActivityViewModel(savedInstanceState: Bundle? = null): T {
        val activity = activity ?: throw IllegalStateException("Fragment not attached to activity")
        val viewModel = ViewModelProviders.of(activity, viewModelFactory)[T::class.java]

        savedInstanceState?.let {
            viewModel.restoreInstanceState(it)
        }

        viewModels[T::class] = viewModel
        return viewModel
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModels.forEach { it.value.saveInstanceState(outState) }
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        viewModels.clear()
        super.onDestroy()
    }

}
