package com.ponomar.shoper.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.FragmentAuthFirstNameBinding
import com.ponomar.shoper.extensions.fadeIn

class AuthFirstNameFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding:FragmentAuthFirstNameBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthFirstNameBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            this.authTitle.fadeIn()
            this.authDesc.fadeIn(delay = 500)
            this.authBlockOfButtonsAndInputUserData.fadeIn(delay=1000)
            authButtonGoToNextStage.setOnClickListener {
                (it.context as FragmentCallBacks).onFragment1NextClick()
            }
        }
    }
}