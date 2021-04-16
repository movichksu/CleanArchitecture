package com.example.cleanarchitechture.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(
        @PrimaryKey val name: String,
        val rate: Int
)
