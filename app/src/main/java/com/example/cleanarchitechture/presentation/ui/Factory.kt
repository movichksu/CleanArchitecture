package com.example.cleanarchitechture.presentation.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cleanarchitechture.domain.PersonRepository
import com.example.cleanarchitechture.domain.PersonUseCase
import com.example.cleanarchitechture.presentation.viewModel.MainViewModel
import java.lang.Exception

class Factory(
    val useCase: PersonUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(useCase) as T
        }
        else throw Exception()
    }
}