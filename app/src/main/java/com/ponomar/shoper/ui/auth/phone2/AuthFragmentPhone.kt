package com.ponomar.shoper.ui.auth.phone2

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ponomar.shoper.databinding.FragmentAuthPhoneBinding
import com.ponomar.shoper.extensions.fadeIn
import com.ponomar.shoper.extensions.getActivity
import com.ponomar.shoper.extensions.observeOnce
import com.ponomar.shoper.extensions.setMask
import com.ponomar.shoper.ui.auth.FragmentCallBacks
import com.ponomar.shoper.util.OnSwipeTouchListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthFragmentPhone : Fragment(){

    private val viewModel:AuthPhoneViewModel by viewModels()
    private lateinit var binding:FragmentAuthPhoneBinding
    private lateinit var firstName:String
    private lateinit var phone:String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firstName = requireArguments().get("firstName") as String
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthPhoneBinding.inflate(inflater)
        return binding.root
    }
    //TODO:BINDING WITHOUT SET TEXT
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.initLiveData()

        binding.apply {
            lifecycleOwner = this@AuthFragmentPhone
            vm = viewModel

            this.authTitle.text = "Классное имя, $firstName!"
            this.authTitle.fadeIn(delay = 200)
            this.authDesc.fadeIn(delay = 700)
            this.authBlockOfButtonsAndInputUserData.fadeIn(delay = 1200)
            this.authEditTextPhone.setMask()

            authButtonGoToNextStage.setOnClickListener {
                phone = authEditTextPhone.text.toString()
                if(phone.isNotEmpty()) {
                        viewModel.sendUserPhoneToGenerateCode(phone)
                }
                else{
                    val toast = Toast.makeText(root.context, "Пустое поле", Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.BOTTOM, 0, 100)
                    toast.show()
                }
            }
            authButtonGoToPreviouslyStage.setOnClickListener {
                (it.context.getActivity() as FragmentCallBacks).onFragment2BackClick()
            }

            root.setOnTouchListener(object : OnSwipeTouchListener(requireContext()){
                override fun onSwipeRight() {
                    super.onSwipeRight()
                    (requireContext().getActivity() as FragmentCallBacks).onFragment2BackClick()
                }

                override fun onSwipeLeft() {
                    super.onSwipeLeft()
                    authButtonGoToNextStage.performClick()
                }
            })

            //TODO:Костыль, не работает once
            viewModel.codeLiveData.observeOnce(viewLifecycleOwner){
                Log.e("code", it.toString())
                Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
                (requireContext().getActivity() as FragmentCallBacks).onFragment2NextClick(firstName, phone)
            }

        }

    }
}