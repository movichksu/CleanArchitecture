package com.example.cleanarchitechture.presentation.ui

import android.content.*
import android.os.BatteryManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.IBinder
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cleanarchitechture.Constants
import com.example.cleanarchitechture.R
import com.example.cleanarchitechture.domain.entity.Person
import com.example.cleanarchitechture.presentation.service.AddPersonService
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

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private val disposable: CompositeDisposable = CompositeDisposable()

    private val batteryBroadcast = BatteryLevelBroadcastReceiver()
    private val addedPersonBroadcast = AddedPersonBroadcastReceiver()

    private var addPersonService: AddPersonService? = null
    private var boundToPersonService: Boolean = false
    private var currentPersonFlag = false
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            boundToPersonService = true
            addPersonService = (service as AddPersonService.PersonServiceBinder).getService()
            if (currentPersonFlag) {
                addPersonService?.startAddPersonProcess(
                    viewModel.personName,
                    viewModel.personRate.toFloat()
                )
                currentPersonFlag = false
            }

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            boundToPersonService = false
            addPersonService = null
        }

    }

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
        swipeRefresh.setOnRefreshListener {
            viewModel.updatePersons()
//            Intent(context, GetPersonService::class.java).also {
//                JobIntentService.enqueueWork(
//                        requireContext(),
//                        GetPersonService::class.java,
//                        123,
//                        Intent(it)
//                )
//            }
        }
        val observable = Observable.create<Unit> { emitter ->
            addPersonBtn.setOnClickListener {
                handleAddPersonClick()
                // emitter.onNext(Unit)
            }
        }
        val subscribe = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { viewModel.registerPerson() }
        disposable.add(subscribe)

        viewModel.getPersons().observe(viewLifecycleOwner, Observer {
            fullListAdapter.setData(it)
            swipeRefresh.isRefreshing = false
        })
        viewModel.getFilteredPersons().observe(viewLifecycleOwner, Observer {
            filteredListAdapter.setData(it)
        })

        viewModel.addItemState.observe(viewLifecycleOwner, Observer {
            stateText.text = getString(
                when (it) {
                    AddItemState.Free -> R.string.free_state
                    AddItemState.Loading -> R.string.loading_state
                    AddItemState.Result -> R.string.result_state
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

        swipeRefresh = view.findViewById(R.id.swipe_refresh)
    }

    private fun handleAddPersonClick() {
        if (boundToPersonService) {
            addPersonService?.startAddPersonProcess(
                viewModel.personName,
                viewModel.personRate.toFloat()
            )
        } else {
            val addPersonServiceIntent = Intent(requireContext(), AddPersonService::class.java).apply {
                this.putExtra(Constants.PERSON_NAME, viewModel.personName)
                this.putExtra(Constants.PERSON_RATE, viewModel.personRate.toFloat())
                currentPersonFlag = true
            }
            requireActivity().bindService(
                addPersonServiceIntent,
                serviceConnection,
                Context.BIND_AUTO_CREATE
            )
        }
    }

    override fun onStart() {
        super.onStart()
        requireActivity().registerReceiver(
            batteryBroadcast,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )
        requireActivity().registerReceiver(
            addedPersonBroadcast,
            IntentFilter(Constants.ADDED_PERSON_ACTION)
        )

    }

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(batteryBroadcast)
        requireActivity().unregisterReceiver(addedPersonBroadcast)
    }

    override fun onClick(person: Person) {
        viewModel.onPersonSelected(person)
    }

    override fun onDestroyView() {
        fullListAdapter.setListener(null)
        filteredListAdapter.setListener(null)
        requireActivity().unbindService(serviceConnection)
        super.onDestroyView()
    }


    inner class BatteryLevelBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            val batteryLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
            rateInput.setText("$batteryLevel")
        }
    }

    inner class AddedPersonBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            viewModel.updatePersons()
        }
    }

}
