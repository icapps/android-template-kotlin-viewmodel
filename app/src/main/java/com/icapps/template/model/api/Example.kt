package com.icapps.template.model.api

import com.squareup.moshi.JsonClass

/**
 * @author maartenvangiel
 * @version 1
 */
@JsonClass(generateAdapter = true)
data class Example(val id: Long, val value: String)