package com.ponomar.shoper.binding

import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.ponomar.shoper.extensions.gone
import com.ponomar.shoper.extensions.goneWithFade
import com.ponomar.shoper.extensions.loadImageByImageUrl
import com.ponomar.shoper.extensions.setMask
import com.ponomar.shoper.model.sqlOutput.CartInnerProduct
import com.ponomar.shoper.model.entities.Product
import com.ponomar.shoper.ui.adapter.CartAdapter
import com.ponomar.shoper.ui.adapter.ProductAdapter


@BindingAdapter("setGone")
fun bindGone(view: View,ifGone:Boolean){
    view.gone(ifGone)
}

@BindingAdapter("setGoneWithFade")
fun bindFadeGone(view:View,ifGone: Boolean){
    view.goneWithFade(ifGone)
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
fun bindProductsAdapter(recyclerView: RecyclerView,products:List<CartInnerProduct>?){
    if(products != null)
    (recyclerView.adapter as? ProductAdapter)?.addProducts(products)
}

@BindingAdapter("adapterCartList")
fun bindCartAdapter(recyclerView: RecyclerView, productsInCartInner:List<CartInnerProduct>?){
    if(productsInCartInner != null) (recyclerView.adapter as? CartAdapter)?.addItems(productsInCartInner)
}


@BindingAdapter("imageUrl")
fun bindImageUrlToImageView(imageView: ImageView,imageUrl:String){
    imageView.loadImageByImageUrl(imageUrl)
}


@BindingAdapter("maskEditText")
fun bindMaskToEditText(editText: EditText,mask:String){
    editText.setMask(mask)
}