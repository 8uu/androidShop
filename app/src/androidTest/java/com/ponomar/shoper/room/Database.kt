package com.ponomar.shoper.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ponomar.shoper.db.AppDB
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class Database {
    
    lateinit var db: AppDB

    @Before
    fun initDB(){
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDB::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun closeDB(){
        db.close()
    }
}