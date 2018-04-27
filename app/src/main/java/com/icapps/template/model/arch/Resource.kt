package com.icapps.template.model.arch

/**
 * @author maartenvangiel
 * @version 1
 */
class Resource<out T>(val status: Status, val data: T? = null, val message: String? = null, val throwable: Throwable? = null) {

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(status = Status.SUCCESS, data = data)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(status = Status.LOADING, data = data)
        }

        fun <T> error(message: String, throwable: Throwable?): Resource<T> {
            return Resource(status = Status.ERROR, message = message, throwable = throwable)
        }
    }

    val isError: Boolean
        get() = status == Status.ERROR

    val isLoading: Boolean
        get() = status == Status.LOADING

    val isSuccess: Boolean
        get() = status == Status.SUCCESS

    override fun toString(): String {
        return "[$status] data = $data, message = $message, throwable = $throwable"
    }

}