package com.ponomar.shoper.ui.auth.login

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
import com.ponomar.shoper.extensions.Auth.Companion.saveAuthToken
import com.ponomar.shoper.extensions.goneWithFade
import com.ponomar.shoper.extensions.setMask
import com.ponomar.shoper.ui.auth.FragmentCallBacks
import com.ponomar.shoper.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding:FragmentAuthLoginBinding
    private val viewModel:LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_auth_login,container,false)
        return binding.root
    }
    //TODO: TWO VIEWMODEL ANOTHER
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@LoginFragment
            vm = viewModel

            authEditTextPhone.setMask()

            authLoginBackToPreviouslyStage.setOnClickListener {
                drawOnlyLoginBlock()
            }

            authButtonLoginSendUserPhone.setOnClickListener {
                val phone:String = authEditTextPhone.text.toString()
                if(phone.isNotEmpty()) {
                    drawLoginAndCodeBlock()
                    viewModel.sendUserPhone(phone)
                }else Toast.makeText(requireContext(),"Пустое поле",Toast.LENGTH_SHORT).show()
            }

            authButtonSendUserCode.setOnClickListener {
                val code:String = authEditTextCode.text.toString()
                if(code.isNotEmpty()){
                    if(code.length == 4) viewModel.sendUserCode(code.toInt())
                    else Toast.makeText(requireContext(),"Не хватает символов",Toast.LENGTH_SHORT).show() //TODO: TO VIEWMODEL?
                }else Toast.makeText(requireContext(),"Поле для кода пустое",Toast.LENGTH_SHORT).show()
            }

            authLoginRegister.setOnClickListener {
                (requireActivity() as FragmentCallBacks).onRegisterBeginClick()
            }


        }

        viewModel.codeLiveData.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_SHORT).show()
        }

        viewModel.tokenLiveData.observe(viewLifecycleOwner){
            requireActivity().saveAuthToken(it)
            MainActivity.startActivity(requireContext())
            requireActivity().finish()
        }
    }

    private fun drawLoginAndCodeBlock(){
        binding.apply {
            authButtonLoginSendUserPhone.goneWithFade(true)
            authLoginCodeBlock.goneWithFade(false)
            authLoginBackToPreviouslyStage.goneWithFade(false)
        }
    }

    private fun drawOnlyLoginBlock(){
        binding.apply {
            authLoginCodeBlock.goneWithFade(true)
            authLoginBackToPreviouslyStage.goneWithFade(true)
            authButtonLoginSendUserPhone.goneWithFade(false)
        }
    }




}