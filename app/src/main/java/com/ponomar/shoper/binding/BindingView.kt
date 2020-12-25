package com.ponomar.shoper.binding

import android.view.View
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.ponomar.shoper.extensions.gone


@BindingAdapter("setGone")
fun bindGone(view: View,ifGone:Boolean){
    view.gone(ifGone)
}


@BindingAdapter("toast")
fun toast(view:View,text:LiveData<String>){
    if(text.value != null) Toast.makeText(view.context,text.value,Toast.LENGTH_SHORT).show()
}