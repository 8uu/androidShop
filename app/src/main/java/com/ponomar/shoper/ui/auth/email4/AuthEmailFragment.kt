package com.ponomar.shoper.ui.auth.email4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ponomar.shoper.databinding.FragmentAuthEmailBinding
import com.ponomar.shoper.extensions.Auth.Companion.getAuthToken
import com.ponomar.shoper.extensions.fadeIn
import com.ponomar.shoper.extensions.getActivity
import com.ponomar.shoper.ui.auth.FragmentCallBacks
import com.ponomar.shoper.util.OnSwipeTouchListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthEmailFragment : Fragment() {

    private val viewModel:AuthEmailViewModel by viewModels()
    private lateinit var binding:FragmentAuthEmailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthEmailBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@AuthEmailFragment

            this.authTitle.fadeIn(delay = 200)
            this.authDesc.fadeIn(delay = 700)
            this.authBlockOfButtonsAndInputUserData.fadeIn(delay = 1200)

            this.authButtonGoToNextStage.setOnClickListener {
                val email:String = this.authEditTextEmail.text.toString()
                if(email.isNotEmpty()) viewModel.updateEmailOfUser(_token = requireActivity().getAuthToken()!!,_email = email)
                else Toast.makeText(requireContext(),"Пустое поле",Toast.LENGTH_LONG).show()
            }
            this.authButtonSkipThisStage.setOnClickListener {
                (requireContext().getActivity() as FragmentCallBacks).onFragment4NextClick()
            }
            this.authButtonGoToPreviouslyStage.setOnClickListener {
                (requireContext().getActivity() as FragmentCallBacks).onFragment4BackClick()
            }

            root.setOnTouchListener(object : OnSwipeTouchListener(requireContext()){
                override fun onSwipeRight() {
                    super.onSwipeRight()
                    (requireContext().getActivity() as FragmentCallBacks).onFragment4BackClick()
                }

                override fun onSwipeLeft() {
                    super.onSwipeLeft()
                    authButtonGoToNextStage.performClick()
                }
            })
        }

        viewModel.statusLiveData.observe(viewLifecycleOwner){
            if(it == 0) (requireContext().getActivity() as FragmentCallBacks).onFragment4NextClick()
        }
    }

}