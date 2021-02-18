package fr.dleurs.android.contactapp

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.dleurs.android.contactapp.application.ContactsApplication
import fr.dleurs.android.contactapp.repository.ContactRepository
import fr.dleurs.android.contactapp.ui.ContactListAdapter
import fr.dleurs.android.contactapp.viewmodel.ContactViewModel

class MainActivity : AppCompatActivity() {

    private val contactViewModel: ContactViewModel by viewModels {
        ContactViewModelFactory(application as ContactsApplication)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ContactListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        contactViewModel.contacts.observe( this) { todos ->
            // Update the cached copy of the words in the adapter.
            todos.let { adapter.submitList(it) }
        }

        val viewModel: ContactViewModel by lazy {
            ViewModelProvider(this, ContactViewModel.Factory(this.application))
                    .get(ContactViewModel::class.java)
        }


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            //val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            //startActivityForResult(intent, createTodoActivityRequestCode)
        }
    }
}

class ContactViewModelFactory(private val application: ContactsApplication) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}