package com.ponomar.shoper.ui.auth

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ponomar.shoper.R
import com.ponomar.shoper.databinding.FragmentAuthFirstNameBinding

class AuthActivity : AppCompatActivity(),FragmentCallBacks {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        navController = findNavController(R.id.auth_host_fragment)
//        val appBarConfiguration = AppBarConfiguration(setOf(
//            R.id.authFirstName,R.id.authPhone))
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onFragment1NextClick() {
        navController.navigate(R.id.action_authFirstName_to_authPhone)
    }

    override fun onFragment2NextClick() {
        navController.navigate(R.id.action_authPhone_to_authCode)
    }

    override fun onFragment3NextClick() {
        navController.navigate(R.id.action_authCode_to_authEmail)
    }

    override fun onFragment4NextClick() {

    }

    override fun onFragment2BackClick() {
        navController.popBackStack()
    }

    override fun onFragment3BackClick() {
        navController.popBackStack()
    }

    override fun onFragment4BackClick() {
        navController.popBackStack()
    }
}