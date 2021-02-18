package fr.dleurs.android.contactapp

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import fr.dleurs.android.contactapp.viewmodel.ContactViewModel

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

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