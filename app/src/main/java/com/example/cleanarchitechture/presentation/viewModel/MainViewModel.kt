package com.example.cleanarchitechture.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitechture.Constants
import com.example.cleanarchitechture.Dependencies
import com.example.cleanarchitechture.domain.*
import com.example.cleanarchitechture.entity.Person
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            withContext(Dispatchers.IO) {
                personsUseCase.registerPerson(personName, personRate.toInt())
            }
            _itemState.value = AddItemState.Result
            setFree()
        }
    }

    init {
//        viewModelScope.launch {
//            personsUseCase.getPersons().collect { personsList ->
//                persons.value = personsList
//                }
//            }
        val subscribe = personsUseCase.getPersonsRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    Log.d(TAG, Thread.currentThread().name)
                }
                .observeOn(Schedulers.io())
                .map { persons ->
                    Log.d(TAG, Thread.currentThread().name)
                    persons.sortedBy { it.name }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d(TAG, Thread.currentThread().name)
                    persons.value = it
                }

        val filteredSubscribe = personsUseCase.getPersonsRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    Log.d(TAG, Thread.currentThread().name)
                }
                .observeOn(Schedulers.io())
                .map { persons ->
                    Log.d(TAG, Thread.currentThread().name)
                    persons.filter { it.rate >= 5 }
                            .sortedBy { it.rate }
                            .filter { it.name.contains("a") }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d(TAG, Thread.currentThread().name)
                    filteredPersons.value = it
                }
        disposable.add(subscribe)
        disposable.add(filteredSubscribe)
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
