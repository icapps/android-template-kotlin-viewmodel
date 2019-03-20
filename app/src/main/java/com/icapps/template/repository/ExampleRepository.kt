package com.icapps.template.repository

import com.icapps.architecture.arch.ObservableFuture
import com.icapps.architecture.arch.asObservable
import com.icapps.architecture.repository.BaseRepository
import com.icapps.template.model.Example
import com.icapps.template.service.ExampleService

/**
 * @author maartenvangiel
 * @version 1
 */
class ExampleRepository(private val exampleService: ExampleService) : BaseRepository() {

    private val examplesCache = mutableMapOf<Long, Example>()

    fun getExample(id: Long): ObservableFuture<Example> {
        synchronized(this) {
            examplesCache[id]?.let { return it.asObservable() }
        }

        return makeCall(exampleService.getExample(id)) peek {
            synchronized(this) {
                examplesCache[id] = it
            }
        }
    }

    fun getExamples(): ObservableFuture<List<Example>> {
        synchronized(this) {
            if (examplesCache.isNotEmpty()) return examplesCache.values.toList().asObservable()
        }

        return makeCall(exampleService.getExamples()) peek { examples ->
            synchronized(this) {
                examplesCache.clear()
                examplesCache.putAll(examples.map { example -> example.id to example })
            }
        }
    }

}