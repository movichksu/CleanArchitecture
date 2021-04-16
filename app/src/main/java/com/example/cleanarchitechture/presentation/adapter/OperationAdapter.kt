package com.example.cleanarchitechture.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitechture.R
import com.example.cleanarchitechture.domain.Operation

class OperationAdapter internal constructor(
        private var data: List<Operation>
) : RecyclerView.Adapter<OperationAdapter.ViewHolder>() {

    private var listener: ItemClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.operation_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = data[position]
        viewHolder.text.text = item.toString()
        viewHolder.container.setOnClickListener {
            listener?.onClick(item)
            //notifyItemRemoved(position)
        }

    }

    override fun getItemCount() = data.size

    fun setData(data: List<Operation>) {
        this.data = data
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container: View = view.findViewById(R.id.operation_container)
        val text: TextView = view.findViewById<TextView>(R.id.operation_text)

    }

    fun setListener(itemClickListener: ItemClickListener?) {
        listener = itemClickListener
    }

}