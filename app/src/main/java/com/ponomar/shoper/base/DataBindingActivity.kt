package com.ponomar.shoper.base

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class DataBindingActivity: AppCompatActivity() {

    protected inline fun <reified T : ViewDataBinding> binding(
        @LayoutRes res: Int
    ): Lazy<T> = lazy { DataBindingUtil.setContentView(this,res) }
}