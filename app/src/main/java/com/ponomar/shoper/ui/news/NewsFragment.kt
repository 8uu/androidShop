package com.ponomar.shoper.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.NewsFragmentBinding
import com.ponomar.shoper.ui.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModels()
    private lateinit var binding:NewsFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.news_fragment, container, false)
        val adapterNews = NewsAdapter()
        val gridLayoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        val spanSizeLookup:SpanSizeLookup = object : SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                return adapterNews.getItemViewSpanSize(position)
            }
        }
        gridLayoutManager.spanSizeLookup = spanSizeLookup
        binding.apply {
            vm = viewModel.apply { fetchNewsAt(0) }
            adapter = adapterNews
            lifecycleOwner = this@NewsFragment
            layoutManager = gridLayoutManager
            swipeRefreshLayout.setOnRefreshListener {
                adapterNews.clearItems()
                viewModel.fetchNewsAt(0)
            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}