package fr.dleurs.android.contactapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import fr.dleurs.android.contactapp.R
import fr.dleurs.android.contactapp.database.ContactDatabase
import fr.dleurs.android.contactapp.model.Contact
import fr.dleurs.android.contactapp.ui.SectionsPagerAdapter
import fr.dleurs.android.contactapp.utils.FabButtonInterface
import fr.dleurs.android.contactapp.viewmodel.ContactViewModel
import timber.log.Timber

class ContactActivity : AppCompatActivity(), FabButtonInterface {


    private lateinit var viewModel: ContactViewModel

    private val createContactActivityRequestCode = 1
    private val detailContactActivityRequestCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ContactViewModel.Factory(this.application)
        ).get(ContactViewModel::class.java)


        setContentView(R.layout.contact_activity)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.contentFragment)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

    }

    override fun goToCreateContactActivity() {
        Timber.i("Create a new contact started")
        val intent = Intent(this, CreateModifyContactActivity::class.java)
        startActivityForResult(intent, createContactActivityRequestCode)
    }

    override fun goToDetailContactActivity() {
        Timber.i("Create a new contact started")
        val intent = Intent(this, CreateModifyContactActivity::class.java)
        startActivityForResult(intent, createContactActivityRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == createContactActivityRequestCode && resultCode == Activity.RESULT_OK) {
            Timber.i("Intent received")
            intentData?.getParcelableExtra<ContactDatabase>("contact")?.let { reply ->
                val newContact = ContactDatabase(firstName = reply.firstName,lastName = reply.lastName, mail = reply.mail)
                Timber.i("Intent received + " + newContact.toString())
                viewModel.insertContact(newContact)
            }
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        }
    }
}

