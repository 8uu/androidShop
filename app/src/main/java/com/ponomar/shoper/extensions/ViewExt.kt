package com.ponomar.shoper.extensions

import android.view.View
import android.widget.ImageView
import androidx.core.view.ViewCompat
import com.ponomar.shoper.R
import com.squareup.picasso.Picasso


fun View.gone(setGone:Boolean, anotherState:Int = View.VISIBLE){
    visibility = if(setGone) View.GONE else anotherState
}

fun View.goneWithFade(setGone: Boolean){
    if(setGone) this.fadeOut()
    else this.fadeIn()
}


fun ImageView.loadImageByImageUrl(imageUrl:String,placeholderID:Int = R.drawable.product_item_placeholder){
    Picasso.get()
        .load(imageUrl)
        .placeholder(placeholderID)
        .into(this)
}


fun View.fadeIn(duration:Long=500,delay:Long = 0){
    this.gone(false)
    this.alpha = 0f
    ViewCompat.animate(this).apply {
        this.alpha(1f)
        this.duration = duration
        this.startDelay = delay
    }
}

fun View.fadeOut(duration: Long = 500,delay: Long = 0,setGone: Boolean = true){
    this.alpha = 1f
    ViewCompat.animate(this).apply {
        this.alpha(0f)
        this.duration = duration
        this.startDelay = delay
        this.withEndAction { this@fadeOut.gone(setGone) }
    }
}