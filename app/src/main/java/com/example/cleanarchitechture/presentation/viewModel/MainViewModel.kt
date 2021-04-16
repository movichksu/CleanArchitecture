package com.example.cleanarchitechture.presentation.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.cleanarchitechture.Dependencies
import com.example.cleanarchitechture.domain.*
import com.example.cleanarchitechture.entity.Person
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
        app: Application
) : AndroidViewModel(app) {

    private val personsUseCase: PersonUseCase by lazy { Dependencies.getPersonUseCase(getApplication<Application>()) }
    private var persons = MutableLiveData<List<Person>>(listOf())
    var personName: String = ""
    var personRate: Int = 0

    private var _calculationState = MutableLiveData<AddItemState>(AddItemState.Free)
    val addItemState: LiveData<AddItemState> = _calculationState


    fun getPersons(): LiveData<List<Person>> {
        return persons
    }

    fun registerPerson() {
        _calculationState.value = AddItemState.Loading
        viewModelScope.launch {
            personsUseCase.registerPerson(personName, personRate)
            _calculationState.value = AddItemState.Result
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
        _calculationState.value = AddItemState.Free
    }

    fun onPersonSelected(person: Person) =
            viewModelScope.launch {
                personsUseCase.removePerson(person)
            }
}
