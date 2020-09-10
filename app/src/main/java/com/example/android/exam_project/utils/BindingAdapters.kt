package com.example.android.exam_project.utils

import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.exam_project.R

// loads the image in the details view
@BindingAdapter("displayImage")
fun displayImage(imageView: ImageView, imageUrl: String?) {

    val imageUri = if (imageUrl.isNullOrBlank()) {
        ""
    } else {
        imageUrl.toUri()
    }

    Glide.with(imageView.context)
        .load(imageUri)
        .apply(
            RequestOptions()
                .error(R.drawable.ic_image_error)
                .centerInside()
        )
        .into(imageView)
}
