package com.example.cleanarchitechture.presentation.adapter

import com.example.cleanarchitechture.domain.Operation

interface ItemClickListener {

    fun onClick(operation: Operation)

}