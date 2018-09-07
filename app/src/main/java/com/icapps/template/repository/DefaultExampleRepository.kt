package com.icapps.template.repository

import com.icapps.architecture.arch.ConcreteMutableObservableFuture
import com.icapps.architecture.arch.ObservableFuture
import com.icapps.architecture.repository.BaseRepository
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
            ConcreteMutableObservableFuture<Example>().apply { onResult(cacheResult) }
        }
    }

    override fun getExamples(): ObservableFuture<List<Example>> {
        return if (examplesCache.isEmpty())
            makeCall(exampleService.getExamples()).peek {
                examplesCache.clear()
                examplesCache.addAll(it)
            }
        else
            ConcreteMutableObservableFuture<List<Example>>().apply { onResult(examplesCache) }
    }

}