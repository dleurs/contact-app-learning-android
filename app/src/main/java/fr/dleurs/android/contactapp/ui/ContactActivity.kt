package fr.dleurs.android.contactapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.dleurs.android.contactapp.R


class ContactActivity : AppCompatActivity() {

    /**
     * Called when the activity is starting.  This is where most initialization
     * should go
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}


/*class MainActivity : AppCompatActivity() {

    private val contactViewModel: ContactViewModel by viewModels {
        ContactViewModelFactory((application as ContactsApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ContactListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        contactViewModel.contacts.observe( this) { contacts ->
            // Update the cached copy of the words in the adapter.
            contacts.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val newContact:ContactDatabase = ContactDatabase(firstName = "Dimitri", lastName = "Lele", mail = "dim@le.com")
            contactViewModel.contacts
            //todoViewModel.insert(TodoRoom(todoName))
            //val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            //startActivityForResult(intent, createTodoActivityRequestCode)
        }
    }
}*/
