package com.example.cleanarchitechture.presentation.viewModel

sealed class CalculationState() {

    object Free : CalculationState()
    object Loading : CalculationState()
    object Rezult : CalculationState()


}