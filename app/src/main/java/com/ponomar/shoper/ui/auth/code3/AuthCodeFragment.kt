package com.ponomar.shoper.ui.auth.code3

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ponomar.shoper.databinding.FragmentAuthCodeBinding
import com.ponomar.shoper.extensions.fadeIn
import com.ponomar.shoper.extensions.getActivity
import com.ponomar.shoper.extensions.observeOnce
import com.ponomar.shoper.ui.auth.FragmentCallBacks
import com.ponomar.shoper.util.OnSwipeTouchListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthCodeFragment : Fragment() {

    private lateinit var binding:FragmentAuthCodeBinding
    private lateinit var firstName:String
    private lateinit var phone:String

    private val viewModel:AuthCodeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firstName = requireArguments().get("firstName") as String
        phone = requireArguments().get("phone") as String
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthCodeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@AuthCodeFragment
            vm = viewModel

            this.authTitle.fadeIn(delay = 200)
            this.authDesc.fadeIn(delay = 700)
            this.authBlockOfButtonsAndInputUserData.fadeIn(delay = 1200)


            this.authButtonGoToNextStage.setOnClickListener {
                val codeStr:String = authEditTextCode.text.toString()
                if(codeStr.isNotEmpty())
                viewModel.sendUserPhoneToGenerateCode(phone,firstName,codeStr.toInt())
                else{
                    val toast = Toast.makeText(requireContext(),"Пустое поле",Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.BOTTOM,0,100)
                    toast.show()
                }
            }

            this.authButtonGoToPreviouslyStage.setOnClickListener {
                (it.context.getActivity() as FragmentCallBacks).onFragment3BackClick()
            }

            root.setOnTouchListener(object : OnSwipeTouchListener(requireContext()){
                override fun onSwipeRight() {
                    super.onSwipeRight()
                    (requireContext().getActivity() as FragmentCallBacks).onFragment3BackClick()
                }
            })

        }

        viewModel.tokenLiveData.observe(viewLifecycleOwner){
                Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
                (requireContext().getActivity() as FragmentCallBacks).saveTokenToSP(it)
                (requireContext().getActivity() as FragmentCallBacks).onFragment3NextClick(phone)
        }

    }




}