package com.icapps.template.di

import androidx.annotation.ColorRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes

/**
 * @author maartenvangiel
 * @version 1
 */
interface ResourcesWrapper {

    fun getString(@StringRes resId: Int): String

    fun getString(@StringRes resId: Int, vararg arguments: Any): String

    fun getInt(@IntegerRes resId: Int): Int

    fun getColor(@ColorRes resId: Int): Int

}