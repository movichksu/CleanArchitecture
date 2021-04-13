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
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitechture.R
import com.example.cleanarchitechture.domain.Operation
import com.example.cleanarchitechture.presentation.adapter.OperationAdapter
import com.example.cleanarchitechture.presentation.adapter.ItemClickListener
import com.example.cleanarchitechture.presentation.viewModel.CalculationState
import com.example.cleanarchitechture.presentation.viewModel.MainViewModel


class MainFragment : Fragment(), ItemClickListener {

    companion object {
        fun newInstance() =
                MainFragment()
    }


    private lateinit var viewModel: MainViewModel
    private lateinit var firstInput: EditText
    private lateinit var secondInput: EditText
    private lateinit var calculateBtn: Button
    private lateinit var operationsList: RecyclerView
    private lateinit var calculationStateText: TextView
    private var adapter = OperationAdapter(listOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        firstInput.doAfterTextChanged {
            viewModel.first = it.toString()
        }
        secondInput.doAfterTextChanged {
            viewModel.second = it.toString()
        }

        calculateBtn.setOnClickListener {
            if (firstInput.text.isNotEmpty() && secondInput.text.isNotEmpty()) {
                val toast = Toast.makeText(requireContext(), "${viewModel.calculate()}", Toast.LENGTH_SHORT)
                toast.show()
            }
            else{
                val toast = Toast.makeText(requireContext(), "input fields are empty!", Toast.LENGTH_SHORT)
                toast.show()
            }
        }

        viewModel.getOperations().observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

        viewModel.calculationState.observe(viewLifecycleOwner, Observer {
            calculationStateText.text = getString(
                    when (it) {
                        CalculationState.Free -> R.string.free_state
                        CalculationState.Loading -> R.string.loading_state
                        CalculationState.Rezult -> R.string.rezult_state
                    }
            )
            when (it) {
                CalculationState.Free -> calculateBtn.isEnabled = true
                CalculationState.Loading -> calculateBtn.isEnabled = false
                CalculationState.Rezult -> calculateBtn.isEnabled = false
            }
        })


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstInput = view.findViewById(R.id.first_input)
        secondInput = view.findViewById(R.id.second_input)
        calculateBtn = view.findViewById(R.id.calculate_btn)
        operationsList = view.findViewById(R.id.operations_list)
        calculationStateText = view.findViewById(R.id.calculation_state_text)

        operationsList.layoutManager = LinearLayoutManager(requireContext())
        operationsList.adapter = adapter
        adapter.setListener(this)


    }

    override fun onClick(operation: Operation) {
        viewModel.onOperationSelected(operation)
    }

    override fun onDestroyView() {
        adapter.setListener(null)
        super.onDestroyView()
    }

}