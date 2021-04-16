package com.example.cleanarchitechture.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitechture.Dependencies
import com.example.cleanarchitechture.domain.CalculateUseCase
import com.example.cleanarchitechture.domain.Operation
import com.example.cleanarchitechture.domain.OperationsUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {


    private val calculateUseCase: CalculateUseCase by lazy { Dependencies.getCalculateUseCase() }
    private val operationsUseCase: OperationsUseCase by lazy { Dependencies.getOperationsUseCase() }
    var first: String = ""
    var second: String = ""

    private var operations = MutableLiveData<List<Operation>>(listOf())

    private var _calculationState = MutableLiveData<CalculationState>(CalculationState.Free)
    val calculationState: LiveData<CalculationState> = _calculationState


    fun getOperations(): LiveData<List<Operation>> {
        return operations
    }


    fun calculate(): Int {
        var rezult: Int = 0
        _calculationState.value = CalculationState.Loading
        viewModelScope.launch {
            rezult = calculateUseCase.calculate(first.toInt(), second.toInt())
            operations.value = operationsUseCase.getOperations()
            _calculationState.value = CalculationState.Rezult
            setFree()
        }
        return rezult
    }

    init {
        viewModelScope.launch {
            operationsUseCase.getOperations().collect {
                operations.value = it
            }
        }
    }

    suspend fun setFree() {
        delay(2000)
        _calculationState.value = CalculationState.Free
    }

    fun onOperationSelected(operation: Operation) =
            viewModelScope.launch {
                operationsUseCase.removeOperation(operation)
                operations.value = operationsUseCase.getOperations()
            }


}