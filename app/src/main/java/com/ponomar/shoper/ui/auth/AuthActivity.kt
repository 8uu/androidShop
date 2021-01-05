package com.ponomar.shoper.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.ponomar.shoper.R
import com.ponomar.shoper.extensions.Auth.Companion.isAuthTokenAvailable
import com.ponomar.shoper.extensions.Auth.Companion.saveAuthToken
import com.ponomar.shoper.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity(),FragmentCallBacks {

    companion object{
        fun startActivity(context: Context){
            val intent = Intent(context, AuthActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) //TODO:LEARN FLAGS
            context.startActivity(intent)
        }
    }

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(isAuthTokenAvailable()) {
            MainActivity.startActivity(this)
            finish()
        }
        else {
            setContentView(R.layout.activity_auth)
            navController = findNavController(R.id.auth_host_fragment)
        }
    }

    override fun onRegisterBeginClick() {
        navController.navigate(R.id.action_authLogin_to_authFirstName)
    }

    override fun onFragment1NextClick(firstName: String) {
        val bundle = Bundle()
        bundle.putString("firstName",firstName)
        navController.navigate(R.id.action_authFirstName_to_authPhone,bundle)
    }

    override fun onFragment2NextClick(firstName: String,phone:String) {
        val bundle = Bundle()
        bundle.putString("firstName",firstName)
        bundle.putString("phone",phone)
        navController.navigate(R.id.action_authPhone_to_authCode,bundle)
    }

    override fun onFragment3NextClick(phone:String) {
        val bundle = Bundle()
        bundle.putString("phone",phone)
        navController.navigate(R.id.action_authCode_to_authEmail,bundle)
    }

    override fun onFragment4NextClick() {
        MainActivity.startActivity(this)
        finish()
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

    override fun saveTokenToSP(token: String) {
        this.saveAuthToken(token)
    }




}