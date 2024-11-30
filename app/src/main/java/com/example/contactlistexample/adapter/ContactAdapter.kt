package com.example.contactlistexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlistexample.R
import com.example.contactlistexample.data.Contact

// Adapter for the RecyclerView
class ContactAdapter(private var contactList: List<Contact>) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    // ViewHolder class to hold item views
    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
    }

    // Inflate the layout for each item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    // Bind data to each item view
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.tvName.text = contact.name
        holder.tvPhone.text = contact.phone
        holder.tvStatus.text = if (contact.isAvailable) "Available" else "Busy"
    }

    // Return the size of the contact list
    override fun getItemCount(): Int = contactList.size

    // Update the contact list and refresh the RecyclerView
    fun updateContacts(newContacts: List<Contact>) {
        contactList = newContacts
        notifyDataSetChanged()
}}