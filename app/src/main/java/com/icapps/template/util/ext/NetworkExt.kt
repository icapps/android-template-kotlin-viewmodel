package com.icapps.template.util.ext

import com.icapps.architecture.arch.ConcreteMutableObservableFuture
import com.icapps.template.model.exception.ServiceException
import okhttp3.Headers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

/**
 * @author Nicola Verbeeck
 * @version 1
 */
inline fun <reified T> Call<T>.wrapToFuture(noinline headerInspector: ((Headers, T) -> T)? = null): ConcreteMutableObservableFuture<T> {
    return wrapToFuture(T::class.java, headerInspector)
}

@Suppress("UNCHECKED_CAST")
fun <T> Call<T>.wrapToFuture(type: Type, headerInspector: ((Headers, T) -> T)? = null): ConcreteMutableObservableFuture<T> {
    val isEmptyBody = type == Unit::class.java
    val isResponseBody = type == ResponseBody::class.java

    val self = this
    val future = object : ConcreteMutableObservableFuture<T>() {
        override fun cancel() {
            super.cancel()
            self.cancel()
        }
    }

    enqueue(object : Callback<T> {
        override fun onResponse(p0: Call<T>?, p1: Response<T>) {
            if (p1.isSuccessful) {
                if (isEmptyBody) {
                    future.onResult(Unit as T)
                } else if (isResponseBody) {
                    val response = if (headerInspector != null)
                        headerInspector(p1.headers(), p1.body() as T)
                    else
                        p1.body() as T

                    future.onResult(response)
                } else {
                    val body = p1.body()
                    if (body == null) {
                        val errorBody = p1.errorBody()?.string()
                        future.onResult(
                                ServiceException("Empty response where a body was expected", errorBody, p1.raw(), p0?.request()))
                    } else {
                        future.onResult(body)
                    }
                }
            } else {
                val errorBody = p1.errorBody()?.string()
                future.onResult(ServiceException(p1.message(), errorBody, p1.raw(), p0?.request()))
            }
        }

        override fun onFailure(p0: Call<T>?, p1: Throwable) {
            future.onResult(ServiceException(p1, p0?.request()))
        }
    })

    return future
}