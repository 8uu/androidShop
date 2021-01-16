package com.ponomar.shoper.ui.order_detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.OrderDetailFragmentBinding
import com.ponomar.shoper.model.entities.Order
import com.ponomar.shoper.ui.adapter.OrderProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailFragment : Fragment() {
    //TODO:TYPESAGE NAVIGATION
    private val viewModel:OrderDetailViewModel by viewModels()
    private lateinit var binding:OrderDetailFragmentBinding
    private lateinit var order:Order
    private lateinit var orderProductAdapter:OrderProductAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        order = requireArguments().getParcelable("order")!!
        binding = DataBindingUtil.inflate(inflater,R.layout.order_detail_fragment,container,false)
        orderProductAdapter = OrderProductAdapter(order.products)
        binding.apply {
            adapter = orderProductAdapter
            vm = viewModel
            userOrder = order
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}