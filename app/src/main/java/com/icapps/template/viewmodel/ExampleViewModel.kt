package com.icapps.template.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.icapps.architecture.arch.BaseViewModel
import com.icapps.architecture.arch.ObservableFuture
import com.icapps.architecture.arch.onCaller
import com.icapps.template.model.Example
import com.icapps.template.model.arch.Resource
import com.icapps.template.repository.ExampleRepository
import javax.inject.Inject

/**
 * @author maartenvangiel
 * @version 1
 */
class ExampleViewModel @Inject constructor(private val exampleRepository: ExampleRepository) : BaseViewModel() {

    val examples = MutableLiveData<Resource<List<Example>>>()

    private var examplesCall: ObservableFuture<*>? = null

    fun init() {
        if (!isCleanInstance) return

        examples.value = Resource.loading()
        examplesCall?.cancel()
        examplesCall = exampleRepository.getExamples() onSuccess {
            examples.value = Resource.success(it)
        } onFailure {
            examples.value = Resource.error("Failed to load examples: ${it.message}", it)
        } observe onCaller
    }

    override fun onCleared() {
        examplesCall?.cancel()
    }
}