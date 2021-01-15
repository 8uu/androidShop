package com.ponomar.shoper.ui.order_history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.OrderHistoryFragmentBinding
import com.ponomar.shoper.extensions.Auth.Companion.getAuthToken
import com.ponomar.shoper.ui.adapter.OrderAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderHistoryFragment : Fragment() {


    private val viewModel:OrderHistoryViewModel by viewModels()
    private lateinit var binding:OrderHistoryFragmentBinding
    private lateinit var orderAdapter:OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.order_history_fragment,container,false)
        orderAdapter = OrderAdapter()
        binding.apply {
            vm = viewModel.apply { fetchHistoryOfOrder(requireActivity().getAuthToken()!!) }
            adapter = orderAdapter
            lifecycleOwner = this@OrderHistoryFragment
            swipeRefreshLayout.setOnRefreshListener {
                orderAdapter.clearItems()
                viewModel.fetchHistoryOfOrder(requireActivity().getAuthToken()!!)
            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}