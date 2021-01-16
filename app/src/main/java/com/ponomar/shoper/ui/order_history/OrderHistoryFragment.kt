package com.ponomar.shoper.ui.order_history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.OrderHistoryFragmentBinding
import com.ponomar.shoper.extensions.Auth.Companion.getAuthToken
import com.ponomar.shoper.ui.adapter.OrderAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderHistoryFragment : Fragment() {

    private val KEY_IS_ALREADY_LOAD:String = "key_as_already_load"

    private val viewModel:OrderHistoryViewModel by viewModels()
    private lateinit var binding:OrderHistoryFragmentBinding
    private lateinit var orderAdapter:OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("STATE","on createview")
        binding = DataBindingUtil.inflate(inflater,R.layout.order_history_fragment,container,false)
        orderAdapter = OrderAdapter(
                onOrderClick = {
                    val bundle = Bundle()
                    bundle.putParcelable("order",it)
                    val bundleSavedState = Bundle()
                    bundleSavedState.putBoolean(KEY_IS_ALREADY_LOAD,true)
                    onSaveInstanceState(bundleSavedState)
                    findNavController().navigate(R.id.action_navigation_order_history_to_navigation_order_detail,bundle)
                }
        )
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


}