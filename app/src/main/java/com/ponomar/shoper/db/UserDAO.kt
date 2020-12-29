package com.ponomar.shoper.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ponomar.shoper.model.entities.User


@Dao
interface UserDAO {

    @Query("DELETE from user;")
    suspend fun nukeTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user:User)


    @Query("SELECT * FROM user LIMIT 1;")
    suspend fun getUser():User?
}