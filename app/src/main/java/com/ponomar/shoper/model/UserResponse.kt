package com.ponomar.shoper.model

import com.ponomar.shoper.model.entities.User

data class UserResponse(
    val status:Int,
    val data:User?
)