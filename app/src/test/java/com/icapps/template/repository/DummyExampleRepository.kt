package com.icapps.template.repository

import com.icapps.template.arch.ObservableFuture
import com.icapps.template.model.Example
import com.icapps.template.model.exception.ServiceException

/**
 * @author maartenvangiel
 * @version 1
 */
class DummyExampleRepository : ExampleRepository {

    override fun getExample(id: Long): ObservableFuture<Example> {
        val result = ObservableFuture<Example>()
        Thread {
            Thread.sleep(100)
            val example = TestData.examples.find { it.id == id }
            if (example == null) {
                result.onResult(ServiceException("Example with id $id not found", null, null, null))
            } else {
                result.onResult(example)
            }
        }.start()
        return result
    }

    override fun getExamples(): ObservableFuture<List<Example>> {
        val result = ObservableFuture<List<Example>>()
        Thread {
            Thread.sleep(100)
            result.onResult(TestData.examples)
        }.start()
        return result
    }

}