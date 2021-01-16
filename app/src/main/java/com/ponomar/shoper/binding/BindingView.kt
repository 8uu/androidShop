package com.ponomar.shoper.binding

import android.annotation.SuppressLint
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.core.view.marginEnd
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ponomar.shoper.R
import com.ponomar.shoper.extensions.gone
import com.ponomar.shoper.extensions.goneWithFade
import com.ponomar.shoper.extensions.loadImageByImageUrl
import com.ponomar.shoper.extensions.setMask
import com.ponomar.shoper.generated.callback.OnClickListener
import com.ponomar.shoper.model.entities.Address
import com.ponomar.shoper.model.entities.News
import com.ponomar.shoper.model.entities.Order
import com.ponomar.shoper.model.sqlOutput.CartInnerProduct
import com.ponomar.shoper.model.entities.Product
import com.ponomar.shoper.ui.adapter.*
import com.ponomar.shoper.ui.order.OnAddressClick


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

@BindingAdapter("adapterOrderList")
fun bindOrderAdapter(recyclerView: RecyclerView,orderList:List<Order>?){
    if(orderList != null) {
            (recyclerView.adapter as? OrderAdapter)?.addItems(orderList)
            Log.e("ordersList",orderList.toString())
    }
}

@BindingAdapter("adapterOrderProductsList")
fun bindOrderProductsAdapter(recyclerView: RecyclerView,orderProducts:List<Product>){
    (recyclerView.adapter as OrderProductAdapter).addItems(orderProducts)
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
fun bindTagListToScrollView(horizontalScrollView: HorizontalScrollView,tags:List<String>?){
    if(tags == null) return
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

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("addressList","addressOnClick")
fun bindAddressesListToScrollView(horizontalScrollView: HorizontalScrollView,addresses:List<Address>?,addressOnClick:OnAddressClick){
    if(addresses == null) return
    if(horizontalScrollView.childCount != 0) horizontalScrollView.removeAllViews() //TODO: WHY
    val context = horizontalScrollView.context
    val containerForTags = LinearLayout(context)
    containerForTags.orientation = LinearLayout.HORIZONTAL
    containerForTags.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
    for(address in addresses){
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.marginEnd = context.resources.getDimensionPixelSize(R.dimen.address_cell_margin_end)
        val tagView = TextView(context)
        tagView.background = context.getDrawable(R.drawable.round_tag)
        tagView.text = address.toString()
        tagView.setPadding(context.resources.getDimensionPixelSize(R.dimen.address_cell_padding))
        tagView.layoutParams = layoutParams
        tagView.setOnClickListener{addressOnClick.onAddressClick(address)}
        containerForTags.addView(tagView)
    }
    horizontalScrollView.addView(containerForTags)
}

@BindingAdapter("setItemDecoration")
fun bindItemDecorationToRecyclerView(recyclerView: RecyclerView,isNeed:Boolean){
    if(isNeed){
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context,DividerItemDecoration.VERTICAL))
    }
}