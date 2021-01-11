package com.ponomar.shoper.ui.order_history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.OrderHistoryFragmentBinding

class OrderHistoryFragment : Fragment() {


    private val viewModel:OrderHistoryViewModel by viewModels()
    private lateinit var binding:OrderHistoryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.order_history_fragment,container,false)
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@OrderHistoryFragment
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}