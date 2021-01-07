package com.ponomar.shoper.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.ItemCartProductBinding
import com.ponomar.shoper.model.sqlOutput.CartInnerProduct
import com.ponomar.shoper.ui.cart.CartViewModel

//TODO:VIEWMODEL?????

class CartAdapter constructor(private val cartViewModel:CartViewModel)
    :RecyclerView.Adapter<CartAdapter.VHolder>() {
    private val items:MutableList<CartInnerProduct> = mutableListOf()

    fun addItems(_items:List<CartInnerProduct>){
        items.addAll(_items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding:ItemCartProductBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_cart_product,
                parent,
                false
        )
        return VHolder(binding)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            itemInCart = item

            cartItemPicMinus.setOnClickListener {
                cartViewModel.onMinusItemClick(item.product.id)
            }

            cartItemPicPlus.setOnClickListener {
                cartViewModel.onPlusItemClick(item.product.id)
            }


        }
    }

    override fun getItemCount(): Int = items.size

    class VHolder(val binding:ItemCartProductBinding):RecyclerView.ViewHolder(binding.root)
}