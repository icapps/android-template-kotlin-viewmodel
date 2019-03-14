package com.icapps.template.viewmodel

import android.arch.lifecycle.Lifecycle
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.icapps.architecture.arch.BaseViewModel
import com.icapps.architecture.utils.logging.logError
import com.icapps.template.model.Example
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

    fun init(lifecycle: Lifecycle) {
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
            errorMessage.set(exception.message)
        } observe lifecycle
    }

}