package com.icapps.template.arch

import java.util.concurrent.CountDownLatch

/**
 * @author Nicola Verbeeck
 * @version 1
 */
open class DirectObservableFuture<T> {

    protected var cancelled = false
    protected val syncLock = Object()
    protected var successCallback: ((T) -> Unit)? = null
    private val peekSuccess: MutableSet<(T) -> Unit> = hashSetOf()
    protected var errorCallback: ((Throwable) -> Unit)? = null

    private var observing: Boolean = false
    private var result: T? = null
    private var error: Throwable? = null

    companion object {
        @JvmStatic
        fun <T> withData(data: T): DirectObservableFuture<T> {
            val future = DirectObservableFuture<T>()
            future.onResult(data)
            return future
        }
    }

    fun observeDirectly(): DirectObservableFuture<T> {
        if (this is ObservableFuture)
            throw IllegalStateException("Do not call observer directly on a regular observable future")
        observeDirectlyInternal()
        return this
    }

    protected fun observeDirectlyInternal() {
        synchronized(syncLock) {
            observing = true
            result?.let { onResult(it) }
            error?.let { onResult(it) }
        }
    }

    /**
     * Can be called multiple times when new data becomes available
     */
    open infix fun onSuccess(callback: (T) -> Unit): DirectObservableFuture<T> {
        synchronized(syncLock) {
            if (!cancelled) {
                successCallback = callback
            }
            if (observing) {
                val res = result ?: return this
                dispatchResult(res)
            }
        }
        return this
    }

    /**
     * Should only be called once
     */
    open infix fun onFailure(callback: (Throwable) -> Unit): DirectObservableFuture<T> {
        synchronized(syncLock) {
            if (!cancelled) {
                errorCallback = callback
            }
            if (observing) {
                val res = error ?: return this
                dispatchResult(res)
            }
        }
        return this
    }

    infix fun peekSuccess(callback: (T) -> Unit): DirectObservableFuture<T> {
        synchronized(syncLock) {
            if (!cancelled) {
                peekSuccess.add(callback)
            }
            val res = result ?: return this
            peekSuccess.forEach { it.invoke(res) }
        }
        return this
    }

    open fun cancel() {
        synchronized(syncLock) {
            cancelled = true
            successCallback = null
            errorCallback = null
            peekSuccess.clear()
            result = null
            error = null
        }
    }

    fun onResult(value: T) {
        synchronized(syncLock) {
            if (!cancelled) {
                error = null
                result = value
                peekSuccess.forEach { it.invoke(value) }
                if (observing)
                    dispatchResult(value)
            }
        }
    }

    fun onResult(error: Throwable) {
        synchronized(syncLock) {
            if (!cancelled) {
                this.error = error
                result = null
                if (observing)
                    dispatchResult(error)
            }
        }
    }

    open fun execute(): T {
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
        observeDirectly()
        latch.await()

        val errResult = err
        if (errResult != null)
            throw errResult

        return res ?: throw IllegalStateException("No error and no result!")
    }

    protected open fun dispatchResult(value: T) {
        try {
            successCallback?.invoke(value)
        } catch (e: Throwable) {
            dispatchResult(e)
        }
    }

    protected open fun dispatchResult(value: Throwable) {
        errorCallback?.invoke(value)
    }

}