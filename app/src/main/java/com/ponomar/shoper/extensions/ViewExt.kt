package com.ponomar.shoper.extensions

import android.view.View
import android.widget.ImageView
import androidx.core.view.ViewCompat
import com.ponomar.shoper.R
import com.squareup.picasso.Picasso


fun View.gone(setGone:Boolean){
    visibility = if(setGone) View.GONE else View.VISIBLE
}


fun ImageView.loadImageByImageUrl(imageUrl:String,placeholderID:Int = R.drawable.product_item_placeholder){
    Picasso.get()
        .load(imageUrl)
        .placeholder(placeholderID)
        .into(this)
}


fun View.fadeIn(duration:Long=500,delay:Long = 0){
    this.alpha = 0f
    ViewCompat.animate(this).apply {
        this.alpha(1f)
        this.duration = duration
        this.startDelay = delay
    }
}