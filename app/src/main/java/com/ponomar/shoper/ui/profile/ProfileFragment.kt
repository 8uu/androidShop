package com.ponomar.shoper.ui.profile

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.ProfileFragmentBinding
import com.ponomar.shoper.extensions.Auth.Companion.forgetAuthToken
import com.ponomar.shoper.extensions.Auth.Companion.getAuthToken
import com.ponomar.shoper.ui.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(),OnSwitchModeClick,OnLogoutClick {

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
            onLogoutClickInterface = this@ProfileFragment

            swipeRefreshLayout.setOnRefreshListener {
                profileViewModel.forceUpdateUserInfo(requireActivity().getAuthToken()!!)
            }
            userProfileButtonGoToLeftFragment.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_profile_to_navigation_address)
            }
        }
        profileViewModel.updateUserInfo(requireActivity().getAuthToken()!!)
    }

    override fun onSwitchModeClick(v: View) {
        val flags:Int = requireContext().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if(flags == Configuration.UI_MODE_NIGHT_YES){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
//        requireActivity().window.setWindowAnimations(R.style.WindowAnimationFadeInOut)
//        requireActivity().recreate()
    }

    override fun onLogoutClick(v: View) {
        requireActivity().forgetAuthToken()
        AuthActivity.startActivity(requireContext())
        requireActivity().finish()
    }
}