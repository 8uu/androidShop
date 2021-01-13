package com.ponomar.shoper.binding

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.core.view.marginEnd
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ponomar.shoper.R
import com.ponomar.shoper.extensions.gone
import com.ponomar.shoper.extensions.goneWithFade
import com.ponomar.shoper.extensions.loadImageByImageUrl
import com.ponomar.shoper.extensions.setMask
import com.ponomar.shoper.model.entities.News
import com.ponomar.shoper.model.sqlOutput.CartInnerProduct
import com.ponomar.shoper.model.entities.Product
import com.ponomar.shoper.ui.adapter.CartAdapter
import com.ponomar.shoper.ui.adapter.NewsAdapter
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
    recyclerView.setHasFixedSize(true)
    recyclerView.setItemViewCacheSize(20)
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

@BindingAdapter("adapterNewsList")
fun bindNewsAdapter(recyclerView: RecyclerView,newsList:List<News>?){
    if(newsList != null) (recyclerView.adapter as? NewsAdapter)?.addItems(newsList)
}


@BindingAdapter("imageUrl")
fun bindImageUrlToImageView(imageView: ImageView,imageUrl:String){
    imageView.loadImageByImageUrl(imageUrl)
}


@BindingAdapter("maskEditText")
fun bindMaskToEditText(editText: EditText,mask:String){
    editText.setMask(mask)
}

@BindingAdapter("isRefreshing")
fun bindOnRefreshToSwipeRefreshLayout(swipeRefreshLayout: SwipeRefreshLayout,isRefreshing:Boolean){
    swipeRefreshLayout.isRefreshing = isRefreshing
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("tagList")
fun bindTagListToScrollView(horizontalScrollView: HorizontalScrollView,tags:List<String>){
    if(horizontalScrollView.childCount != 0) horizontalScrollView.removeAllViews() //TODO: WHY
    val context = horizontalScrollView.context
    val containerForTags = LinearLayout(context)
    containerForTags.orientation = LinearLayout.HORIZONTAL
    containerForTags.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
    for(tag in tags){
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.marginEnd = context.resources.getDimensionPixelSize(R.dimen.tag_view_margin_end)
        val tagView = TextView(context)
        tagView.background = context.getDrawable(R.drawable.round_tag)
        tagView.text = tag
        tagView.setPadding(context.resources.getDimensionPixelSize(R.dimen.tag_view_padding))
        tagView.layoutParams = layoutParams
        containerForTags.addView(tagView)
    }
    horizontalScrollView.addView(containerForTags)
}