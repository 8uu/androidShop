package com.ponomar.shoper.ui.auth.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.FragmentAuthLoginBinding
import com.ponomar.shoper.extensions.*
import com.ponomar.shoper.extensions.Auth.Companion.saveAuthToken
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

            viewModel.codeLiveData.observe(viewLifecycleOwner){
                drawLoginAndCodeBlock()
            }

            authButtonLoginSendUserPhone.setOnClickListener {
                val phone:String = authEditTextPhone.text.toString()
                if(phone.isNotEmpty()) {
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
            authButtonLoginSendUserPhone.moveViewTo(authButtonSendUserCode.x - authButtonLoginSendUserPhone.x,
                    authButtonSendUserCode.y - authButtonLoginSendUserPhone.y) {
                it.gone(true)
                authEditTextCodeContainer.visibility = View.VISIBLE
                authButtonSendUserCode.visibility = View.VISIBLE
                authEditTextCodeContainer.fadeIn(300)
                authButtonSendUserCode.alpha = 1f
                authLoginBackToPreviouslyStage.fadeIn(300)
            }
            authEditTextCode.requestFocus()
        }
    }

    private fun drawOnlyLoginBlock(){
        binding.apply {
            authEditTextCodeContainer.fadeOut(600)
            authLoginBackToPreviouslyStage.fadeOut(300)
            authButtonSendUserCode.moveViewTo(-authButtonSendUserCode.x + authButtonLoginSendUserPhone.x,
                    -authButtonSendUserCode.y + authButtonLoginSendUserPhone.y){
                authButtonSendUserCode.alpha = 0f
                authButtonSendUserCode.visibility = View.INVISIBLE
                authButtonLoginSendUserPhone.gone(false)
                authEditTextCodeContainer.visibility = View.INVISIBLE
            }
            authEditTextPhone.requestFocus()
        }
    }




}