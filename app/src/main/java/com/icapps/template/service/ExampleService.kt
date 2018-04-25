package com.icapps.template.service

import com.icapps.template.model.Example
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author maartenvangiel
 * @version 1
 */
interface ExampleService {

    @GET("examples/{id}")
    fun getExample(@Path("id") id: Long): Call<Example>

    @GET("examples")
    fun getExamples(): Call<List<Example>>

}