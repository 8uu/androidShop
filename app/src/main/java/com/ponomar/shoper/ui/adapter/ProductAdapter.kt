package com.ponomar.shoper.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.ItemProductBinding
import com.ponomar.shoper.model.entities.Product

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.VHolder>() {

    private val items:MutableList<Product> = mutableListOf()


    fun addProducts(_items:List<Product>){
        items.addAll(_items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding:ItemProductBinding = DataBindingUtil.inflate(inflater, R.layout.item_product,parent,false)
        return VHolder(binding)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            product = item
            executePendingBindings()
        }

    }

    override fun getItemCount(): Int = items.size

    class VHolder(val binding:ItemProductBinding):RecyclerView.ViewHolder(binding.root)
}