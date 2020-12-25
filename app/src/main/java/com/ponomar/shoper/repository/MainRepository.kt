package com.ponomar.shoper.repository

import com.ponomar.shoper.db.AppDB
import com.ponomar.shoper.network.Client
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MainRepository @Inject constructor(
    client: Client,
    appDB: AppDB
){
}