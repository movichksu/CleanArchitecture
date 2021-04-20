package com.example.cleanarchitechture.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitechture.Dependencies
import com.example.cleanarchitechture.domain.*
import com.example.cleanarchitechture.entity.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val personsUseCase: PersonUseCase by lazy { Dependencies.getPersonUseCase() }
    private var persons = MutableLiveData<List<Person>>(listOf())
    var personName: String = ""
    var personRate: String = ""

    private var _addItemState = MutableLiveData<AddItemState>(AddItemState.Free)
    val addItemState: LiveData<AddItemState> = _addItemState


    fun getPersons(): LiveData<List<Person>> {
        return persons
    }

    fun registerPerson() {
        viewModelScope.launch {
            _addItemState.value = AddItemState.Loading
            withContext(Dispatchers.IO) {
                personsUseCase.registerPerson(personName, personRate.toInt())
            }
            _addItemState.value = AddItemState.Result
            setFree()
        }
    }

    init {
        viewModelScope.launch {
            personsUseCase.getPersons().collect {
                persons.value = it
            }
        }
    }

    suspend fun setFree() {
        delay(2000)
        _addItemState.value = AddItemState.Free
    }

    fun onPersonSelected(person: Person) =
            viewModelScope.launch {
                personsUseCase.removePerson(person)
            }
}
