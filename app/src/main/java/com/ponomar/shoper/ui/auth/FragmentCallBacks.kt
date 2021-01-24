package com.ponomar.shoper.ui.auth

interface FragmentCallBacks {
    fun onRegisterBeginClick()

    fun onFragment1NextClick(firstName:String) //TODO:maybe refactor
    fun onFragment2NextClick(firstName: String,phone:String)
    fun onFragment3NextClick(phone:String)
    fun onFragment4NextClick()


    fun onFragment1BackClick()
    fun onFragment2BackClick()
    fun onFragment3BackClick()
    fun onFragment4BackClick()

    fun saveTokenToSP(token:String)
}