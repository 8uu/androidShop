package com.ponomar.shoper.model.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


@Entity(foreignKeys = [ForeignKey(
    entity = Product::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("pid"),
    onDelete = CASCADE,
    onUpdate = CASCADE,
    deferred = true
)]
)
data class Cart(
        @PrimaryKey val pid:Int,
        var quantity:Int
    )
