package com.example.cleanarchitechture.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleanarchitechture.Dependencies
import com.example.cleanarchitechture.domain.CalculateUseCase
import com.example.cleanarchitechture.domain.Operation
import com.example.cleanarchitechture.domain.OperationsUseCase

class MainViewModel : ViewModel() {


    private val calculateUseCase: CalculateUseCase by lazy { Dependencies.getCalculateUseCase() }
    private val operationsUseCase: OperationsUseCase by lazy { Dependencies.getOperationsUseCase() }
    var first: String = ""
    var second: String = ""

    private var operations = MutableLiveData<List<Operation>>(listOf())

    fun getOperations(): LiveData<List<Operation>>{
        return operations
    }


    fun calculate(): Int {
        val rezult = calculateUseCase.calculate(first.toInt(), second.toInt())
        operations.value = operationsUseCase.getOperations()
        return rezult

    }

    init{

        operations.value = operationsUseCase.getOperations()

    }




}