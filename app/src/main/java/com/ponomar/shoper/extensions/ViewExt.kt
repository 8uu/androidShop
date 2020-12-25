package com.ponomar.shoper.extensions

import android.view.View


fun View.gone(setGone:Boolean){
    visibility = if(setGone) View.GONE else View.VISIBLE
}