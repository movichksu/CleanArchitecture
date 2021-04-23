package com.example.cleanarchitechture.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.cleanarchitechture.entity.Person

class PersonCompareCallback: DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }
}