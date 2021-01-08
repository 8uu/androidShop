package com.ponomar.shoper.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.ItemProductBinding
import com.ponomar.shoper.extensions.getActivity
import com.ponomar.shoper.model.entities.Product
import com.ponomar.shoper.model.sqlOutput.CartInnerProduct
import com.ponomar.shoper.ui.detail.ProductDetailFragment


class ProductAdapter : RecyclerView.Adapter<ProductAdapter.VHolder>() {

    private val items:MutableList<CartInnerProduct> = mutableListOf()


    fun addProducts(_items: List<CartInnerProduct>){
        items.addAll(_items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding:ItemProductBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.item_product,
            parent,
            false
        )
        return VHolder(binding)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            product = item.product
            executePendingBindings()//TODO:WHAT
            root.setOnClickListener {
                val detailFragment = ProductDetailFragment(item)
                detailFragment.show(it.context.getActivity()!!.supportFragmentManager, null)
            }
        }

    }

    override fun getItemCount(): Int = items.size

    class VHolder(val binding: ItemProductBinding):RecyclerView.ViewHolder(binding.root)
}