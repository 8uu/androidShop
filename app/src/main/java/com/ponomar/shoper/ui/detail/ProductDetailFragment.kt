package com.ponomar.shoper.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.FragmentDetailProductBinding
import com.ponomar.shoper.extensions.gone
import com.ponomar.shoper.model.entities.Cart
import com.ponomar.shoper.model.sqlOutput.CartInnerProduct
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail_product.*


@AndroidEntryPoint
class ProductDetailFragment(
        _productInfo: CartInnerProduct,
        private val onChangeCartInfo: (Cart?) -> Unit
) : BottomSheetDialogFragment() {


    private lateinit var binding:FragmentDetailProductBinding
    private val viewModel: ProductDetailViewModel by viewModels()
    private var _product = _productInfo.product
    private var cartInfo = _productInfo.cartInfo

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_product, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = this@ProductDetailFragment
            product = _product

            fragmentDetailButtonAddInCart.setOnClickListener {
                fragmentDetailContainerWithButton.gone(true)
                fragmentDetailContainerWithCounter.gone(false)
                cartInfo = Cart(_product.id, 0)
                onChangeCartInfo(cartInfo)
                viewModel.plusQuantityInCart(_product.id)
                updateQuantity()
            }

            fragmentDetailButtonPlus.setOnClickListener {
                viewModel.plusQuantityInCart(_product.id)
            }

            fragmentDetailButtonMinus.setOnClickListener {
                viewModel.minusQuantityInCart(_product.id)
            }

            if(cartInfo != null) {
                fragmentDetailContainerWithButton.gone(true)
                fragmentDetailContainerWithCounter.gone(false)
                updateQuantity()
            }

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel.incStatusLiveData.observe(viewLifecycleOwner){
            Log.e("pid+", it.toString())
            ++cartInfo!!.quantity
            updateQuantity()
        }

        viewModel.decStatusLiveData.observe(viewLifecycleOwner){
            Log.e("pid", it.toString())
            if(--cartInfo!!.quantity == 0){
                binding.fragmentDetailContainerWithButton.gone(false)
                binding.fragmentDetailContainerWithCounter.gone(true)
                onChangeCartInfo(null)
                Log.e("cart", cartInfo.toString())
            }else updateQuantity()
        }
    }

    private fun updateQuantity(){
        binding.fragmentDetailQuantity.text = cartInfo!!.quantity.toString()
    }



}