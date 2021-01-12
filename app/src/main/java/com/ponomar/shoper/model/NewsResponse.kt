package com.ponomar.shoper.model

import com.google.gson.annotations.SerializedName
import com.ponomar.shoper.model.entities.News

data class NewsResponse(
        val status:Int,
        @SerializedName("data") val news:List<News>
)
