package com.clementecastillo.citiboxtest.client

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.clementecastillo.citiboxtest.R
import com.clementecastillo.citiboxtest.extension.toPx


object ImageLoader {

    fun load(uri: Uri, target: ImageView, width: Int = 0, height: Int = 0, rounded: Boolean = false, errorDrawable: Drawable? = null) {
        GlideApp.with(target.context)
            .load(uri)
            .apply(getGlideOptions(target, width, height, rounded, errorDrawable))
            .into(target)
    }

    fun loadUserAvatar(userId: Int, target: ImageView, rounded: Boolean = false) {
        load(Uri.parse("https://api.adorable.io/avatars/$userId"), target, target.width, target.height, rounded)
    }

    @SuppressLint("CheckResult")
    private fun getGlideOptions(target: ImageView, width: Int = 0, height: Int = 0, rounded: Boolean = false, errorDrawable: Drawable? = null): RequestOptions {
        val options = RequestOptions()

        val transformations: MutableList<BitmapTransformation> = mutableListOf()

        if (width > 0 || height > 0) {
            options.override(width, height)
            transformations.add(CenterCrop())
        }

        if (rounded) {
            transformations.add(CircleCrop())
        } else

            if (errorDrawable != null) {
                options.error(errorDrawable)
            }

        LottieDrawable()
        options.placeholder(CircularProgressDrawable(target.context).apply {
            strokeWidth = 5.toPx().toFloat()
            centerRadius = minOf(width, height).let {
                if (it > 0) {
                    it * 0.10f
                } else {
                    10.toPx().toFloat()
                }
            }
            setColorSchemeColors(ContextCompat.getColor(target.context, R.color.colorAccent))
            start()
        })

        if (transformations.isNotEmpty()) {
            options.transforms(*transformations.toTypedArray())
        }
        return options
    }

}