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
import com.example.cleanarchitechture.presentation.adapter.OperationAdapter
import com.example.cleanarchitechture.presentation.viewModel.CalculationState
import com.example.cleanarchitechture.presentation.viewModel.MainViewModel


class MainFragment : Fragment() {

    companion object {
        fun newInstance() =
            MainFragment()
    }

   // private lateinit var operationContainer: View

    private lateinit var viewModel: MainViewModel
    private lateinit var firstInput: EditText
    private lateinit var secondInput: EditText
    private lateinit var calculateBtn: Button
    private lateinit var operations: RecyclerView
    private lateinit var calculationStateText: TextView
    private var adapter = OperationAdapter(mutableListOf())

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
            val toast = Toast.makeText(requireContext(), "${viewModel.calculate()}", Toast.LENGTH_SHORT )
            toast.show()
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
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstInput = view.findViewById(R.id.input_first)
        secondInput = view.findViewById(R.id.input_second)
        calculateBtn = view.findViewById(R.id.calculate_btn)
        operations = view.findViewById(R.id.operations_list)
        calculationStateText = view.findViewById(R.id.calculation_state_text)
//        operationContainer = view.findViewById(R.id.operation_container)

        operations.layoutManager = LinearLayoutManager(requireContext())
        operations.adapter = adapter


    }

}