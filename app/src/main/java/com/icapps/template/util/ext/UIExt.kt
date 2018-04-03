package com.icapps.template.util.ext

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.res.Resources
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * @author maartenvangiel
 * @version 1
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <T : ViewDataBinding> Int.bindContentView(activity: Activity): T {
    return DataBindingUtil.setContentView(activity, this)!!
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T : ViewDataBinding> Int.inflate(layoutInflater: LayoutInflater, into: ViewGroup?, attach: Boolean = false): T {
    return DataBindingUtil.inflate(layoutInflater, this, into, attach)!!
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T : ViewDataBinding> Int.inflate(context: Context, into: ViewGroup?, attach: Boolean = false): T {
    return inflate(LayoutInflater.from(context), into, attach)
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T : ViewDataBinding> Int.inflate(into: ViewGroup, attach: Boolean = false): T {
    return inflate(LayoutInflater.from(into.context), into, attach)
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T : ViewDataBinding> Int.inflate(fragment: Fragment, into: ViewGroup?, attach: Boolean = false): T {
    return inflate(fragment.activity, into, attach)
}

@Suppress("NOTHING_TO_INLINE")
inline fun Int.str(context: Context): String = context.getString(this)

@Suppress("NOTHING_TO_INLINE", "SpreadOperator")
inline fun Int.str(context: Context, vararg formatArgs: Any): String = context.getString(this, *formatArgs)

@Suppress("NOTHING_TO_INLINE")
inline fun Int.str(resources: Resources): String = resources.getString(this)

@Suppress("NOTHING_TO_INLINE")
@ColorInt
inline fun Int.color(context: Context): Int = ContextCompat.getColor(context, this)

@Suppress("NOTHING_TO_INLINE")
inline fun Int.drawable(context: Context): Drawable? = ContextCompat.getDrawable(context, this)

@Suppress("NOTHING_TO_INLINE")
inline fun Int.dpi(context: Context): Int = context.resources.getDimensionPixelSize(this)

@Suppress("NOTHING_TO_INLINE")
inline fun Int.intRes(context: Context): Int = context.resources.getInteger(this)