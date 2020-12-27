package com.ponomar.shoper.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.FragmentAuthPhoneBinding

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

        binding.authButtonGoToPreviouslyStage.setOnClickListener {
            (it.context as FragmentCallBacks).onFragment2BackClick()
        }

    }
}