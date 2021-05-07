package com.example.cleanarchitechture.presentation.adapter

import com.example.cleanarchitechture.domain.entity.Person

interface ItemClickListener {

    fun onClick(person: Person)
}
