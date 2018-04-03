package com.icapps.template.model.exception

import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * @author maartenvangiel
 * @version 1
 */
class ServiceException : IOException {
    val response: Response?
    val request: Request?
    val errorBody: String?

    constructor(message: String, errorBody: String?, response: Response?, request: Request?) : super(message) {
        this.response = response
        this.request = request
        this.errorBody = errorBody
    }

    constructor(other: Throwable, request: Request?) : super(other) {
        this.response = null
        this.request = request
        this.errorBody = null
    }

    override val message: String?
        get() = buildString {
            append(super.message)
            response?.let {
                append(" - ").append(it.code())
                append(" - url: ").append(it.request()?.url())
                append(" - cache: ").append(it.cacheControl())
            }
        }

}