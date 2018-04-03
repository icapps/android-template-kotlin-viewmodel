package com.icapps.template.arch

import android.arch.lifecycle.ViewModel
import android.os.Bundle

/**
 * @author maartenvangiel
 * @version 1
 */
abstract class BaseViewModel : ViewModel() {

    open fun saveInstanceState(outState: Bundle) {
        // Implement in subclass
    }

    open fun restoreInstanceState(savedInstanceState: Bundle) {
        // Implement in subclass
    }

}