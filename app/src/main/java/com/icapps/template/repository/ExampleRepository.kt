package com.icapps.template.repository

import com.icapps.template.arch.ObservableFuture
import com.icapps.template.model.Example

/**
 * @author maartenvangiel
 * @version 1
 */
interface ExampleRepository {

    fun getExample(id: Long): ObservableFuture<Example>

    fun getExamples(): ObservableFuture<List<Example>>

}