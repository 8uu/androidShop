package com.ponomar.shoper.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.ItemNewsBinding
import com.ponomar.shoper.extensions.loadImageByImageUrl
import com.ponomar.shoper.model.entities.News

class NewsAdapter:RecyclerView.Adapter<NewsAdapter.NewsHolder>() {
    companion object{
        const val TYPE_LARGE = 2
        const val TYPE_SMALL = 1
    }
    private lateinit var binding:ItemNewsBinding
    private val news:MutableList<News> = mutableListOf()

    class NewsHolder constructor(binding:ItemNewsBinding):RecyclerView.ViewHolder(binding.root)

    fun addItems(_news:List<News>){
        news.addAll(_news)
        notifyDataSetChanged()
    }

    fun clearItems() = news.clear()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_news,parent,false)
        return NewsHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val item = news[position]
        binding.newsItemsMainNewsImage.loadImageByImageUrl(item.imageURL,placeholderID = R.drawable.news_item_placeholder)
    }

    override fun getItemCount(): Int = news.size

    fun getItemViewSpanSize(position: Int): Int {
        return if(news[position].type == "large") TYPE_LARGE else TYPE_SMALL
    }
}