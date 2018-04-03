package com.icapps.template.webservice

import com.icapps.template.model.Example
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author maartenvangiel
 * @version 1
 */
interface ExampleService {

    @GET("example/{id}")
    fun getExample(@Path("id") id: Long): Call<Example>

    @GET("example")
    fun getExamples(): Call<List<Example>>

}