package com.ponomar.shoper.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.FragmentCartBinding
import com.ponomar.shoper.ui.adapter.CartAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter
    private lateinit var binding:FragmentCartBinding

//TODO:FIX CART UPDATE AND CLEAN UP!!!!!!!!!!!!
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart,container,false)
        cartAdapter = CartAdapter(cartViewModel)
        binding.apply {
            lifecycleOwner = this@CartFragment
            vm = cartViewModel
            adapter = cartAdapter
            fragmentCartButtonMakeOrder.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_cart_to_navigation_order)
            }//TODO:MAYBE CHECK EMPTY CART HERE
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel.decStatusLiveData.observe(viewLifecycleOwner){
            cartAdapter.onMinusClick(it)
        }

        cartViewModel.incStatusLiveData.observe(viewLifecycleOwner){
            cartAdapter.onPlusClick(it)
        }

        cartViewModel.cartInnerLiveData.observe(viewLifecycleOwner){
            if(it.isEmpty()) binding.fragmentCartButtonMakeOrder.isClickable = false
        }
    }
}