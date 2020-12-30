package com.ponomar.shoper.db

import androidx.room.*
import com.ponomar.shoper.model.entities.User


@Dao
interface UserDAO {

    @Query("DELETE from user;")
    suspend fun nukeTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user:User)


    @Query("SELECT * FROM user LIMIT 1;")
    suspend fun getUser():User?

    @Query("UPDATE user set email = :email where id = :uid;")
    suspend fun updateEmail(uid:Int,email:String)
}