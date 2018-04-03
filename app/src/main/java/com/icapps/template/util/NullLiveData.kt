package com.icapps.template.util

import android.arch.lifecycle.LiveData

/**
 * @author maartenvangiel
 * @version 1
 */
class NullLiveData<T> : LiveData<T>() {

    init {
        postValue(null)
    }

}