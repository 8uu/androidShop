package com.ponomar.shoper.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.OrderFragmentBinding
import com.ponomar.shoper.extensions.Auth.Companion.getAuthToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment() {
    private val viewModel:OrderViewModel by viewModels()
    private lateinit var binding:OrderFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.order_fragment,container,false)
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@OrderFragment
            fragmentOrderButtonMakeOrder.setOnClickListener {
                viewModel.makeOrderRequest(
                    fragmentOrderTextViewAddressDistrict.text.toString(),
                    fragmentOrderTextViewAddressStreet.text.toString(),
                    fragmentOrderTextViewAddressHouse.text.toString(),
                    fragmentOrderTextViewAddressFlat.text.toString().toInt(),
                    requireActivity().getAuthToken()!!
                )
            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.statusResponseLiveData.observe(viewLifecycleOwner){
            if(it == 0){
                Toast.makeText(requireContext(),"С Вами свяжется наш оператор",Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.navigation_menu)
            }else{
                Toast.makeText(requireContext(),"Что-то не то",Toast.LENGTH_LONG).show()
            }
        }
    }

}