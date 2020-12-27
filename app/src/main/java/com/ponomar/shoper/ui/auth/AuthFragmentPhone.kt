package com.ponomar.shoper.ui.auth

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ponomar.shoper.databinding.FragmentAuthPhoneBinding
import com.ponomar.shoper.extensions.fadeIn


class AuthFragmentPhone : Fragment() {

    private lateinit var binding:FragmentAuthPhoneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthPhoneBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.authButtonGoToNextStage.setOnClickListener {
            (it.context as FragmentCallBacks).onFragment2NextClick()
        }



        binding.apply {
            this.authTitle.fadeIn(delay = 200)
            this.authDesc.fadeIn(delay = 700)
            this.authBlockOfButtonsAndInputUserData.fadeIn(delay = 1200)

            authButtonGoToPreviouslyStage.setOnClickListener {
                (it.context as FragmentCallBacks).onFragment2BackClick()
            }

        }

    }
}