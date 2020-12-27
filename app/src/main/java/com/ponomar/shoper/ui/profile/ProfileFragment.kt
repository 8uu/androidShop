package com.ponomar.shoper.ui.profile

import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ponomar.shoper.R
import com.ponomar.shoper.base.DataBindingActivity
import com.ponomar.shoper.databinding.ProfileFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(),OnSwitchModeClick {

    private val profileViewModel:ProfileViewModel by viewModels()
    private lateinit var binding:ProfileFragmentBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.profile_fragment,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@ProfileFragment
            vm = profileViewModel
            onSwitchNightModeInterface = this@ProfileFragment

        }
    }

    override fun onSwitchModeClick(v: View) {
        val flags:Int = requireContext().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val uiManager:UiModeManager = requireContext().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        if(flags == Configuration.UI_MODE_NIGHT_YES){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Log.e("ON CLICK","1")
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
//        requireActivity().window.setWindowAnimations(R.style.WindowAnimationFadeInOut)
//        requireActivity().recreate()
    }
}