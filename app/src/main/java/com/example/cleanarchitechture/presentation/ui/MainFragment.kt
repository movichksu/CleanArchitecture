package com.example.cleanarchitechture.presentation.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitechture.R
import com.example.cleanarchitechture.entity.Person
//import com.example.cleanarchitechture.presentation.adapter.OperationAdapter
import com.example.cleanarchitechture.presentation.adapter.ItemClickListener
import com.example.cleanarchitechture.presentation.adapter.PersonAdapter
import com.example.cleanarchitechture.presentation.viewModel.AddItemState
import com.example.cleanarchitechture.presentation.viewModel.MainViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainFragment : Fragment(), ItemClickListener {

    companion object {
        fun newInstance() =
            MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var nameInput: EditText
    private lateinit var rateInput: EditText
    private lateinit var addPersonBtn: Button
    private lateinit var personsList: RecyclerView
    private lateinit var filteredPersonsList: RecyclerView
    private lateinit var stateText: TextView
    private var fullListAdapter = PersonAdapter(listOf())
    private var filteredListAdapter = PersonAdapter(listOf())
    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        nameInput.doAfterTextChanged {
            viewModel.personName = it.toString()
        }
        rateInput.doAfterTextChanged {
            viewModel.personRate = it.toString()
        }

        val observable = Observable.create<Unit> { emitter ->
            addPersonBtn.setOnClickListener {
                emitter.onNext(Unit)
            }
        }
        val subscribe = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { viewModel.registerPerson() }
        disposable.add(subscribe)

        viewModel.getPersons().observe(viewLifecycleOwner, Observer {
            fullListAdapter.setData(it)
        })
        viewModel.getFilteredPersons().observe(viewLifecycleOwner, Observer {
            filteredListAdapter.setData(it)
        })

        viewModel.addItemState.observe(viewLifecycleOwner, Observer {
            stateText.text = getString(
                    when (it) {
                        AddItemState.Free -> R.string.free_state
                        AddItemState.Loading -> R.string.loading_state
                        AddItemState.Result -> R.string.rezult_state
                    }
            )
            when (it) {
                AddItemState.Free -> addPersonBtn.isEnabled = true
                AddItemState.Loading -> addPersonBtn.isEnabled = false
                AddItemState.Result -> addPersonBtn.isEnabled = false
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addPersonBtn = view.findViewById(R.id.add_btn)
        personsList = view.findViewById(R.id.persons_list)
        filteredPersonsList = view.findViewById(R.id.filtered_persons_list)
        stateText = view.findViewById(R.id.state_text)
        nameInput = view.findViewById(R.id.name_input)
        rateInput = view.findViewById(R.id.rate_input)

        personsList.layoutManager = LinearLayoutManager(requireContext())
        personsList.adapter = fullListAdapter
        fullListAdapter.setListener(this)

        filteredPersonsList.layoutManager = LinearLayoutManager(requireContext())
        filteredPersonsList.adapter = filteredListAdapter
        filteredListAdapter.setListener(this)
    }

    override fun onClick(person: Person) {
        viewModel.onPersonSelected(person)
    }

    override fun onDestroyView() {
        fullListAdapter.setListener(null)
        super.onDestroyView()
    }
}
