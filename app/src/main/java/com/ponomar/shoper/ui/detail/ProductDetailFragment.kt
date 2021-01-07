package com.ponomar.shoper.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.FragmentDetailProductBinding
import com.ponomar.shoper.model.entities.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment(private val _product:Product) : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(_product:Product) = ProductDetailFragment(_product)
    }

    private lateinit var binding:FragmentDetailProductBinding
    private val viewModel: ProductDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail_product,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@ProductDetailFragment
            product = _product
        }
    }

}