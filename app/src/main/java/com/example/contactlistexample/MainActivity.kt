package com.example.contactlistexample

import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlistexample.adapter.ContactAdapter
import com.example.contactlistexample.data.Contact
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ContactAdapter
    private val contactList = mutableListOf<Contact>()
    private fun generateInitialContacts() {
        val initialContacts = listOf(
            Contact("Cristobal", "123123", true),
            Contact("Fabian", "213123", false),
            Contact("Claudia", "213132", true),
            Contact("Cesar", "3231321", false)
        )
        contactList.addAll(initialContacts)
    }
    private var isFilteringAvailable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find views
        val etName = findViewById<EditText>(R.id.etName)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val cbAvailable = findViewById<CheckBox>(R.id.cbAvailable)
        val fabAddContact = findViewById<FloatingActionButton>(R.id.fabAddContact)
        val btnFilterAvailable = findViewById<Button>(R.id.filterButton)

        generateInitialContacts()
        setRecyclerViewAdapter(contactList)

        fabAddContact.setOnClickListener {
            addContact(etName, etPhone, cbAvailable)
        }

        btnFilterAvailable.setOnClickListener {
            isFilteringAvailable = !isFilteringAvailable
            if (isFilteringAvailable) {
                val availableContacts = contactList.filter { it.isAvailable }
                adapter.updateContacts(availableContacts)
                btnFilterAvailable.text = getString(R.string.quitar_filtro)
            } else {
                adapter.updateContacts(contactList)
                btnFilterAvailable.text = getString(R.string.mostrar_disponibles)
            }
        }
    }

    private fun setRecyclerViewAdapter(contactList: List<Contact>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = ContactAdapter(contactList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun addContact(etName: EditText, etPhone: EditText, cbAvailable: CheckBox) {
        val name = etName.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val isAvailable = cbAvailable.isChecked

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, getString(R.string.por_favor_llenar_todos_los_campos), Toast.LENGTH_SHORT).show()
            return
        }

        val newContact = Contact(name, phone, isAvailable)
        contactList.add(newContact)
        adapter.notifyItemInserted(contactList.size - 1)

        etName.text.clear()
        etPhone.text.clear()
        cbAvailable.isChecked = false

        Toast.makeText(this, getString(R.string.contacto_agregado_exitosamente), Toast.LENGTH_SHORT).show()
    }
}