package com.icapps.template.databinding

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

/**
 * @author maartenvangiel
 * @version 1
 */
object TextViewBinding {

    @JvmStatic
    @BindingAdapter("startDrawable")
    @SuppressWarnings("MagicNumber")
    fun bindStartDrawable(textView: TextView, imageResource: Int) {
        val drawable = if (imageResource == 0) {
            null
        } else {
            ContextCompat.getDrawable(textView.context, imageResource)
        }
        val drawables = textView.compoundDrawables
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, drawables[1], drawables[2], drawables[3])
    }

    @JvmStatic
    @BindingAdapter("endDrawable")
    @SuppressWarnings("MagicNumber")
    fun bindEndDrawable(textView: TextView, imageResource: Int) {
        val drawable = if (imageResource == 0) {
            null
        } else {
            ContextCompat.getDrawable(textView.context, imageResource)
        }
        val drawables = textView.compoundDrawables
        textView.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawable, drawables[3])
    }

    @JvmStatic
    @BindingAdapter("topDrawable")
    @SuppressWarnings("MagicNumber")
    fun bindTopDrawable(textView: TextView, imageResource: Int) {
        val drawable = if (imageResource == 0) {
            null
        } else {
            ContextCompat.getDrawable(textView.context, imageResource)
        }
        val drawables = textView.compoundDrawables
        textView.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawable, drawables[2], drawables[3])
    }

}