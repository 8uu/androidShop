package com.ponomar.shoper.extensions

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity

fun Context.getActivity(): AppCompatActivity? {
    return when (this) {
        is AppCompatActivity -> this
        is ContextWrapper -> this.baseContext.getActivity()
        else -> null
    }
}