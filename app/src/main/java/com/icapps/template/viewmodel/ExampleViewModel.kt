package com.icapps.template.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import com.icapps.architecture.arch.BaseViewModel
import com.icapps.architecture.utils.logging.logError
import com.icapps.template.R
import com.icapps.template.di.ResourcesWrapper
import com.icapps.template.model.api.Example
import com.icapps.template.repository.ExampleRepository
import javax.inject.Inject

/**
 * @author maartenvangiel
 * @version 1
 */
class ExampleViewModel @Inject constructor(private val exampleRepository: ExampleRepository) : BaseViewModel() {

    val loading = ObservableBoolean()
    val examples = ObservableField<List<Example>>()
    val errorMessage = ObservableField<String>()

    fun init(lifecycle: Lifecycle, resourcesWrapper: ResourcesWrapper) {
        if (!examples.get().isNullOrEmpty()) return

        loading.set(true)
        exampleRepository.getExamples() onSuccess {
            examples.set(it)
            loading.set(false)
            errorMessage.set(null)
        } onFailure { exception ->
            logError("Failed to load examples: ", exception)
            examples.set(null)
            loading.set(false)
            errorMessage.set(resourcesWrapper.getString(R.string.general_label_error))
        } observe lifecycle
    }

}