package com.icapps.template.fragment

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.icapps.architecture.arch.BaseViewModel
import com.icapps.architecture.controller.ViewModelLifecycleController
import com.icapps.template.di.ResourcesWrapper
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * @author maartenvangiel
 * @version 1
 */
abstract class BaseFragment : DaggerFragment(), ResourcesWrapper {

    @Inject
    lateinit var viewModelLifecycleController: ViewModelLifecycleController

    protected inline val resourcesWrapper: ResourcesWrapper
        get() = this

    protected inline fun <reified T : BaseViewModel> getOrCreateViewModel(savedInstanceState: Bundle? = null): T {
        return viewModelLifecycleController.getOrCreateViewModel(this, savedInstanceState)
    }

    protected inline fun <reified T : BaseViewModel> getOrCreateActivityViewModel(): T {
        return viewModelLifecycleController.getOrCreateViewModel(requireActivity(), null) // savedInstanceState should be handled by the activity, not the fragment.
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

    override fun getColor(resId: Int): Int {
        return ContextCompat.getColor(requireContext(), resId)
    }

}
