package com.icapps.template.arch

import android.os.Handler
import android.os.Looper
import java.util.concurrent.CountDownLatch

/**
 * @author Nicola Verbeeck
 * @version 1
 */
open class ObservableFuture<T> : DirectObservableFuture<T>() {

    companion object {
        @JvmStatic
        fun <T> withData(data: T): ObservableFuture<T> {
            val future = ObservableFuture<T>()
            future.onResult(data)
            return future
        }

        @JvmStatic
        fun <T> withError(error: Throwable): ObservableFuture<T> {
            val future = ObservableFuture<T>()
            future.onResult(error)
            return future
        }

        @JvmStatic
        private val mainHandler: Handler = Handler(Looper.getMainLooper())
    }

    private var dispatchingThread: Handler? = null

    fun observe(): ObservableFuture<T> {
        val looper = Looper.myLooper()
        val handler = if (looper == null)
            mainHandler
        else
            Handler(looper)
        return observe(handler)
    }

    fun observe(dispatchingHandler: Handler): ObservableFuture<T> {
        synchronized(syncLock) {
            if (dispatchingThread != null)
                throw IllegalStateException("Observe called multiple times")
            dispatchingThread = dispatchingHandler

            observeDirectlyInternal()
        }
        return this
    }

    override fun dispatchResult(value: T) {
        val dispatcher = synchronized(syncLock) {
            dispatchingThread
        } ?: return
        if (dispatcher.looper == Looper.myLooper()) {
            if (!cancelled) {
                try {
                    successCallback?.invoke(value)
                } catch (e: Throwable) {
                    errorCallback?.invoke(e)
                }
            }
        } else {
            dispatcher.post {
                synchronized(syncLock) {
                    if (cancelled)
                        return@post
                    try {
                        successCallback?.invoke(value)
                    } catch (e: Throwable) {
                        errorCallback?.invoke(e)
                    }
                }
            }
        }
    }

    override fun dispatchResult(value: Throwable) {
        val dispatcher = synchronized(syncLock) {
            dispatchingThread
        } ?: return
        if (dispatcher.looper == Looper.myLooper()) {
            if (!cancelled)
                errorCallback?.invoke(value)
        } else {
            dispatcher.post {
                synchronized(syncLock) {
                    if (cancelled)
                        return@post
                    errorCallback?.invoke(value)
                }
            }
        }
    }

    override fun onSuccess(callback: (T) -> Unit): ObservableFuture<T> {
        super.onSuccess(callback)
        return this
    }

    override fun onFailure(callback: (Throwable) -> Unit): ObservableFuture<T> {
        super.onFailure(callback)
        return this
    }

    override fun execute(): T {
        if (Looper.getMainLooper() == Looper.myLooper())
            throw IllegalStateException("Execute cannot be called on observable future, this will deadlock on the main thread")

        var res: T? = null
        var err: Throwable? = null
        val latch = CountDownLatch(1)
        onSuccess {
            res = it
            latch.countDown()
        } onFailure {
            err = it
            latch.countDown()
        }
        observe(mainHandler)
        latch.await()

        val errorResult = err
        if (errorResult != null)
            throw errorResult

        return res ?: throw IllegalStateException("No error and no result")
    }
}

fun <T> DirectObservableFuture<T>.toDispatching(): ObservableFuture<T> {
    if (this is ObservableFuture<T>)
        return this
    val future = ObservableFuture<T>()
    onSuccess(future::onResult)
    onFailure(future::onResult)
    observeDirectly()
    return future
}

fun <T> ObservableFuture<T>.bind(source: DirectObservableFuture<T>): ObservableFuture<T> {
    source.onSuccess(this::onResult)
    source.onFailure(this::onResult)
    source.observeDirectly()
    return this
}
