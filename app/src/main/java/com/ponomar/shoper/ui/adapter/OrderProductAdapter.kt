package com.ponomar.shoper.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.ItemOrderProductBinding
import com.ponomar.shoper.model.entities.Product

class OrderProductAdapter(private val products:List<Product>):RecyclerView.Adapter<OrderProductAdapter.VHolder>() {
    class VHolder(val binding:ItemOrderProductBinding):RecyclerView.ViewHolder(binding.root)

    fun addItems(_products:List<Product>){
//        products.addAll(_products)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val binding:ItemOrderProductBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_order_product,
                parent,
                false)
        Log.e("onCreate","viewHolder")
        return VHolder(binding)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val product = products[position]
        Log.e("product",product.toString())
        holder.binding.product = product
    }

    override fun getItemCount() = products.size
}