package com.example.cleanarchitechture.presentation.viewModel

sealed class AddItemState() {

    object Free : AddItemState()
    object Loading : AddItemState()
    object Result : AddItemState()
}
