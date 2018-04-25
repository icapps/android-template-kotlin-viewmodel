package com.icapps.template.repository

import com.icapps.template.arch.ObservableFuture
import com.icapps.template.arch.toDispatching
import com.icapps.template.model.Example
import com.icapps.template.service.ExampleService

/**
 * @author maartenvangiel
 * @version 1
 */
class DefaultExampleRepository(private val exampleService: ExampleService) : BaseRepository(), ExampleRepository {

    private val examplesCache = mutableListOf<Example>()

    override fun getExample(id: Long): ObservableFuture<Example> {
        val cacheResult = examplesCache.find { it.id == id }
        return if (cacheResult == null) {
            makeCall(exampleService.getExample(id))
        } else {
            ObservableFuture<Example>().apply { onResult(cacheResult) }
        }
    }

    override fun getExamples(): ObservableFuture<List<Example>> {
        return if (examplesCache.isEmpty())
            makeCall(exampleService.getExamples()).peekSuccess({
                examplesCache.clear()
                examplesCache.addAll(it)
            }).toDispatching()
        else
            ObservableFuture<List<Example>>().apply { onResult(examplesCache) }
    }

}