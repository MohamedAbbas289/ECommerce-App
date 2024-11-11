package com.example.ecommerceapp.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.ecommerceapp.R
import com.google.android.material.textfield.TextInputLayout


@BindingAdapter("error")
fun bindErrorOnTextInputLayout(textInputLayout: TextInputLayout, errorMessage: String?) {

    textInputLayout.error = errorMessage
    Log.d("test error", "error received")
}

@BindingAdapter("url")
fun bindImageWithUrl(
    imageView: ImageView,
    url: String?
) {
    Glide.with(imageView)
        .load(url)
        .placeholder(R.drawable.ic_launcher_foreground)
        .into(imageView)

}