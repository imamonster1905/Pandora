package com.esmac.android.pandora.util.extention

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import java.io.File

/**
 * Created by Hữu Nguyễn on 13/07/2020.
 * Email: huuntt1905@gmail.com.
 */


@BindingAdapter("glideSrc")
fun ImageView.setGlideSrc(@DrawableRes src: Int?) {
    Glide.with(context)
        .load(src)
        .into(this)
}

@BindingAdapter("glidePath")
fun ImageView.setGlidePath(imagePath: String) {
    Glide.with(context)
        .load(File(imagePath))
        .into(this)
}

@BindingAdapter("loadImageUrlNormal")
fun ImageView.loadImageUrlNormal(image: String?) {
    Glide.with(context)
        .load(image)
        .error(placeHolder)
        .placeholder(placeHolder)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

@BindingAdapter("loadImageUrl")
fun ImageView.loadImageUrl(image: String?) {
    Glide.with(context)
        .load(image)
        .centerCrop()
        .error(placeHolder)
        .placeholder(placeHolder)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

@BindingAdapter(
    value = ["loadImage", "placeholder", "centerCrop", "fitCenter", "circleCrop", "cacheSource", "animation", "large"],
    requireAll = false
)
fun ImageView.loadImage(
    url: String? = "", @DrawableRes placeHolder: Drawable?,
    centerCrop: Boolean = false, fitCenter: Boolean = false, circleCrop: Boolean = false,
    isCacheSource: Boolean = false, animation: Boolean = false, isLarge: Boolean = false
) {

    if (url.isNullOrBlank()) {
        setImageDrawable(placeHolder)
        return
    }
    val requestBuilder = Glide.with(context).load(url)
    val requestOptions = RequestOptions().diskCacheStrategy(
        if (isCacheSource) DiskCacheStrategy.DATA else DiskCacheStrategy.RESOURCE
    )
        .placeholder(placeHolder)

    if (animation.not()) requestOptions.dontAnimate()
    if (centerCrop) requestOptions.centerCrop()
    if (fitCenter) requestOptions.fitCenter()
    if (circleCrop) requestOptions.circleCrop()
    val file = File(url)
    if (file.exists()) {
        requestOptions.signature(ObjectKey(file.lastModified().toString()))
    }
    requestBuilder.apply(requestOptions).into(this)
}
