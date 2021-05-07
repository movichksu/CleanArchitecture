package com.example.cleanarchitechture.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Person(
        @PrimaryKey val name: String,

        @SerializedName("rating")
        val rate: Float
)
