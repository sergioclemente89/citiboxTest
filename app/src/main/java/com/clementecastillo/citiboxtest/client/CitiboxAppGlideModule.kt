package com.clementecastillo.citiboxtest.client

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Priority
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions


@GlideModule
class CitiboxAppGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val options = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .downsample(DownsampleStrategy.AT_MOST)
            .priority(Priority.HIGH)
        builder.setDefaultRequestOptions(options)
    }
}