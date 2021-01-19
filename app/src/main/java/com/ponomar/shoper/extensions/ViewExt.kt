package com.ponomar.shoper.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.ponomar.shoper.R
import com.ponomar.shoper.util.MaskFormat
import com.squareup.picasso.Picasso


fun View.gone(setGone: Boolean, anotherState: Int = View.VISIBLE){
    visibility = if(setGone) View.GONE else anotherState
}

fun View.goneWithFade(setGone: Boolean){
    if(setGone) this.fadeOut()
    else this.fadeIn()
}


fun ImageView.loadImageByImageUrl(imageUrl: String, placeholderID: Int = R.drawable.product_item_placeholder){
    Picasso.get()
        .load(imageUrl)
        .placeholder(placeholderID)
        .into(this)
}


fun View.fadeIn(duration: Long = 500, delay: Long = 0){
    this.gone(false)
    this.alpha = 0f
    ViewCompat.animate(this).apply {
        this.alpha(1f)
        this.duration = duration
        this.startDelay = delay
    }
}

fun View.fadeOut(duration: Long = 500, delay: Long = 0, setGone: Boolean = true){
    this.alpha = 1f
    ViewCompat.animate(this).apply {
        this.alpha(0f)
        this.duration = duration
        this.startDelay = delay
        this.withEndAction { this@fadeOut.gone(setGone) }
    }
}

fun TextView.setMask(mask: String = MaskFormat.PHONE_MASK){
    MaskFormat.installOn(this, mask)
}

fun View.reverseVisibility(animationIDForGone: Int = R.anim.slide_up,
                           animationIDForVisible: Int = R.anim.slide_down,
                           onViewToChange:View
):Int{
    var visibilityOfView = -1
    if(visibility == View.VISIBLE){
        visibilityOfView = View.GONE
        val animation = AnimationUtils.loadAnimation(context,R.anim.slide_up)
        animation.fillAfter = true
        animation.setAnimationListener(object:Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                visibility = View.GONE
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })
        startAnimation(animation)
    }else if(visibility == View.GONE){
        visibilityOfView = View.VISIBLE
        val animation = AnimationUtils.loadAnimation(context,R.anim.slide_down)
        animation.fillAfter = true
        visibility = View.VISIBLE
        startAnimation(animation)
    }
    return visibilityOfView
}

fun View.moveUpViewToHeight(height:Int){
    
}

