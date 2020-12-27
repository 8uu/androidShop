package com.ponomar.shoper.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.FragmentAuthCodeBinding
import com.ponomar.shoper.extensions.fadeIn

class AuthCodeFragment : Fragment() {

    private lateinit var binding:FragmentAuthCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthCodeBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            this.authTitle.fadeIn(delay = 200)
            this.authDesc.fadeIn(delay = 700)
            this.authBlockOfButtonsAndInputUserData.fadeIn(delay = 1200)

            this.authButtonGoToNextStage.setOnClickListener {
                (it.context as FragmentCallBacks).onFragment3NextClick()
            }

            this.authButtonGoToPreviouslyStage.setOnClickListener {
                (it.context as FragmentCallBacks).onFragment3BackClick()
            }
        }
    }

}