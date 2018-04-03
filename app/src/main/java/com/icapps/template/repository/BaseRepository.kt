package com.icapps.template.repository

import com.icapps.template.arch.DirectObservableFuture
import com.icapps.template.arch.ObservableFuture
import com.icapps.template.arch.toDispatching
import com.icapps.template.util.ext.wrapToFuture
import retrofit2.Call

/**
 * @author maartenvangiel
 * @version 1
 */
abstract class BaseRepository {

    inline fun <reified T> makeCall(call: Call<T>): ObservableFuture<T> {
        return makeCall<T, T>(T::class.java, call, transform = null).toDispatching()
    }

    inline fun <reified T> makeRawCall(call: Call<T>): DirectObservableFuture<T> {
        return makeCall(T::class.java, call, transform = null)
    }

    inline fun <reified T, O> makeCall(call: Call<T>, noinline transform: ((T) -> O)): ObservableFuture<O> {
        return makeCall(T::class.java, call, transform = transform).toDispatching()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T, O> makeCall(type: Class<T>, call: Call<T>, transform: ((T) -> O)?): DirectObservableFuture<O> {
        if (transform == null)
            return call.wrapToFuture(type) as DirectObservableFuture<O>

        val wrapped = DirectObservableFuture<O>()
        val direct = call.wrapToFuture(type)
        direct.onSuccess { wrapped.onResult(transform(it)) }
        direct.onFailure(wrapped::onResult)
        direct.observeDirectly()

        return wrapped
    }

}