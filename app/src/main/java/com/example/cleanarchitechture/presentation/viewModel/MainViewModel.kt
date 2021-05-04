package com.example.cleanarchitechture.presentation.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.cleanarchitechture.App
import com.example.cleanarchitechture.Constants
import com.example.cleanarchitechture.Dependencies
import com.example.cleanarchitechture.domain.*
import com.example.cleanarchitechture.entity.Person
import com.example.cleanarchitechture.presentation.worker.addPersonWorker
import com.example.cleanarchitechture.presentation.worker.getPersonsWorker
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {

    companion object {
        const val TAG = Constants.TAG + " viewModel"
    }

    private val personsUseCase: PersonUseCase by lazy { Dependencies.getPersonUseCase() }
    var personName: String = ""
    var personRate: String = ""

    private var persons = MutableLiveData<List<Person>>(listOf())
    private var filteredPersons = MutableLiveData<List<Person>>(listOf())
    private var _itemState = MutableLiveData<AddItemState>(AddItemState.Free)
    val addItemState: LiveData<AddItemState> = _itemState

    private val disposable = CompositeDisposable()

    fun getPersons(): LiveData<List<Person>> {
        return persons
    }

    fun getFilteredPersons(): LiveData<List<Person>> {
        return filteredPersons
    }

    fun registerPerson() {
        viewModelScope.launch {
            _itemState.value = AddItemState.Loading
            //personsUseCase.registerPerson(personName, personRate.toFloat())
            val name = workDataOf(Constants.PERSON_NAME to personName)
            val rate = workDataOf(Constants.PERSON_RATE to personRate.toFloat())
            val addPersonRequest = OneTimeWorkRequestBuilder<addPersonWorker>()
                    .setInputData(name)
                    .setInputData(rate)
                    .build()
            WorkManager.getInstance().enqueue(addPersonRequest)
            updatePersons()

            _itemState.value = AddItemState.Result
            setFree()
        }
    }

    @SuppressLint("IdleBatteryChargingConstraints")
    fun updatePersons() {
//        viewModelScope.launch {
//            personsUseCase.getPersons().also {
//                withContext(Dispatchers.Main) {
//                    persons.value = it
//                }
//            }
//        }
        val getPersonsRequest = OneTimeWorkRequestBuilder<getPersonsWorker>()
                . build()
        WorkManager.getInstance().enqueue(getPersonsRequest)
    }

    init {
        updatePersons()
        viewModelScope.launch {
            personsUseCase.subscribePersons().collect {
                persons.value = it
            }
        }
//        getInit()
    }

    fun getInit(){
        viewModelScope.launch {
            persons.value = personsUseCase.getPersons()
        }
    }

    suspend fun setFree() {
        delay(2000)
        _itemState.value = AddItemState.Free
    }

    fun onPersonSelected(person: Person) =
        viewModelScope.launch {
            personsUseCase.removePerson(person)
        }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}
