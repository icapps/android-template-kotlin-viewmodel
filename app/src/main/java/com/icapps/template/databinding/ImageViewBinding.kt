package com.icapps.template.databinding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

/**
 * @author maartenvangiel
 * @version 1
 */
object ImageViewBinding {

    @JvmStatic
    @BindingAdapter("imageUrl", "placeHolder")
    fun bindAsyncImage(imageView: ImageView, url: String?, placeHolderResource: Int) {
        if (url == null) {
            loadPlaceholder(imageView, placeHolderResource)
            return
        }
        val builder = Picasso.get()
                .load(url)

        if (placeHolderResource == View.NO_ID)
            builder.placeholder(imageView.drawable)
        else if (placeHolderResource != 0)
            builder.placeholder(placeHolderResource)

        imageView.setImageDrawable(null)
        builder.into(imageView)
    }

    @JvmStatic
    private fun loadPlaceholder(imageView: ImageView, placeHolderResource: Int) {
        if (placeHolderResource == 0 || placeHolderResource == View.NO_ID)
            imageView.setImageDrawable(null)
        else
            imageView.setImageResource(placeHolderResource)
    }

}