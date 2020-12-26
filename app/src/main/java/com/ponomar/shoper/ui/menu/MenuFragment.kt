package com.ponomar.shoper.ui.menu

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
import com.ponomar.shoper.base.DataBindingActivity
import com.ponomar.shoper.databinding.FragmentMenuBinding
import com.ponomar.shoper.ui.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private val menuViewModel: MenuViewModel by viewModels()
    private lateinit var binding:FragmentMenuBinding


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_menu,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@MenuFragment
            adapter = ProductAdapter()
            vm = menuViewModel.apply { updateListOfProducts(0) }
        }
    }
}