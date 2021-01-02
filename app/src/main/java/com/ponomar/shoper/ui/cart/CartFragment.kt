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
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var binding:FragmentCartBinding


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart,container,false)
        binding.apply {
            lifecycleOwner = this@CartFragment
            vm = cartViewModel.apply { fetchCartData() }
        }
        return binding.root
    }
}