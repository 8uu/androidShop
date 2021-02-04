package com.ponomar.shoper.model.entities.converters

import androidx.room.TypeConverter
import java.util.*
import java.util.stream.Collectors


class StringConverter {


    @TypeConverter
    fun fromStrings(images:List<String>):String{
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            images.stream().collect(Collectors.joining(","))
        }else{
            var s = ""
            for(i in 0..images.size){
                s += images[i]
                if(i != images.size - 1) s += ","
            }
            s
        }
    }

    @TypeConverter
    fun toArray(imagesStr:String):List<String>{
        return imagesStr.split(',')
    }
}