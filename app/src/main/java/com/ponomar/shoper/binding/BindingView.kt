package com.ponomar.shoper.binding

import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.ponomar.shoper.extensions.gone
import com.ponomar.shoper.extensions.loadImageByImageUrl
import com.ponomar.shoper.model.entities.Product
import com.ponomar.shoper.ui.adapter.ProductAdapter


@BindingAdapter("setGone")
fun bindGone(view: View,ifGone:Boolean){
    view.gone(ifGone)
}


@BindingAdapter("toast")
fun toast(view:View,text:LiveData<String>){
    if(text.value != null) Toast.makeText(view.context,text.value,Toast.LENGTH_SHORT).show()
}


@BindingAdapter("adapter")
fun bindAdapter(recyclerView: RecyclerView,adapter: RecyclerView.Adapter<*>){
    recyclerView.adapter = adapter
    recyclerView.setHasFixedSize(true);
    recyclerView.setItemViewCacheSize(20);
}


@BindingAdapter("adapterProductsList")
fun bindProductsAdapter(recyclerView: RecyclerView,products:List<Product>?){
    if(products != null)
    (recyclerView.adapter as? ProductAdapter)?.addProducts(products)
}


@BindingAdapter("imageUrl")
fun bindImageUrlToImageView(imageView: ImageView,imageUrl:String){
    imageView.loadImageByImageUrl(imageUrl)
}