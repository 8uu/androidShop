package com.ponomar.shoper.ui.menu

import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.fragment_menu.*

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private val menuViewModel: MenuViewModel by viewModels()
    private lateinit var binding:FragmentMenuBinding
    private lateinit var _adapter:ProductAdapter


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
        _adapter = ProductAdapter()
        binding.apply {
            lifecycleOwner = this@MenuFragment
            adapter = _adapter
            vm = menuViewModel.apply { updateListOfProducts(0) }

            swipeRefreshLayout.setOnRefreshListener {
                _adapter.clearProducts()
                menuViewModel.updateListOfProducts(0)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e("STATE","on start")
    }

    override fun onResume() {
        super.onResume()
        Log.e("STATE","on resume")
    }
}