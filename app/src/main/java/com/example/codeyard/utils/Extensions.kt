package com.example.codeyard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

fun Context.launchActivity(destination: Class<*>, user: User) {
    val intent = Intent(this, destination)
    val bundle = Bundle()
    bundle.putParcelable(BUNDLE_KEY, user)
    intent.putExtra(INTENT_KEY, bundle)
    this.startActivity(intent)
}

fun Context.displayToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}