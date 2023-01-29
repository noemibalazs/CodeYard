package com.example.codeyard.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.codeyard.R
import com.example.codeyard.User

@BindingAdapter("userName")
fun bindUserName(view: AppCompatTextView, user: User) {
    view.text =
        view.context.getString(R.string.user_name_label, user.name.firstName, user.name.lastName)
}

@BindingAdapter("userAddress")
fun bindUserAddress(view: AppCompatTextView, user: User) {
    view.text =
        String.format(
            view.context.getString(R.string.user_address_label), user.location.city,
            user.location.street.name,
            user.location.street.number
        )
}

@BindingAdapter("userAvatar")
fun bindUserAvatar(view: AppCompatImageView, user: User) {
    Glide.with(view.context).load(user.picture.medium)
        .circleCrop()
        .placeholder(R.drawable.ic_face)
        .error(R.drawable.ic_face)
        .into(view)
}