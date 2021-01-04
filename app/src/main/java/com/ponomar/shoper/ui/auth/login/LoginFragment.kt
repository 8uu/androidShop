package com.ponomar.shoper.ui.auth.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.FragmentAuthLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding:FragmentAuthLoginBinding
    private val viewModel:LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_auth_login,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@LoginFragment
            vm = viewModel
        }

        viewModel.codeLiveData.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_SHORT).show()
        }
    }




}