package com.example.cleanarchitechture.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitechture.R
import com.example.cleanarchitechture.entity.Person

class PersonAdapter internal constructor(
        private var persons: List<Person>
) : RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    private var listener: ItemClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.person_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val person = persons[position]
        viewHolder.personName.text = person.name
        viewHolder.personRate.text = " - ${person.rate}"
        viewHolder.container.setOnClickListener {
            listener?.onClick(person)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount() = persons.size

    fun setData(data: List<Person>) {
        this.persons = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container: View = view.findViewById(R.id.person_container)
        val personName: TextView = view.findViewById<TextView>(R.id.person_name_text)
        val personRate: TextView = view.findViewById<TextView>(R.id.person_rate_text)

    }

    fun setListener(itemClickListener: ItemClickListener?) {
        listener = itemClickListener
    }
}
