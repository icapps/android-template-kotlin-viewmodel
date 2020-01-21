package com.icapps.template.repository

import com.icapps.architecture.arch.ObservableFuture
import com.icapps.architecture.arch.asObservable
import com.icapps.architecture.repository.BaseRepository
import com.icapps.architecture.utils.caching.Cached
import com.icapps.template.model.Constants
import com.icapps.template.model.api.Example
import com.icapps.template.service.ExampleService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author maartenvangiel
 * @version 1
 */
@Singleton
class ExampleRepository @Inject constructor(private val exampleService: ExampleService) : BaseRepository() {

    private var examplesCache: List<Example>? by Cached(Constants.DEFAULT_CACHE_VALIDITY)

    fun getExamples(): ObservableFuture<List<Example>> {
        synchronized(this) {
            examplesCache?.let {
                if (it.isNotEmpty()) return it.asObservable()
            }
        }

        return makeCall(exampleService.getExamples()) peek { examples ->
            synchronized(this) {
                examplesCache = examples
            }
        }
    }

}