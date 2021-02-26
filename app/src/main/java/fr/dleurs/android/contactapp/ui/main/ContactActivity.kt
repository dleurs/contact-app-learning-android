package fr.dleurs.android.contactapp.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import fr.dleurs.android.contactapp.R
import fr.dleurs.android.contactapp.database.ContactDatabase
import fr.dleurs.android.contactapp.model.Contact
import fr.dleurs.android.contactapp.ui.detailsContact.DetailsContactActivity
import fr.dleurs.android.contactapp.ui.newModifyContact.CreateModifyContactActivity
import fr.dleurs.android.contactapp.utils.FabButtonInterface
import fr.dleurs.android.contactapp.viewmodel.ContactViewModel
import timber.log.Timber

class ContactActivity : AppCompatActivity(), FabButtonInterface, OnClick {


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
        val sectionsPagerAdapter =
            SectionsPagerAdapter(context = this, fm = supportFragmentManager, onClick = this)
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
        Timber.i("Details contact started")
        val intent = Intent(this, DetailsContactActivity::class.java)
        startActivityForResult(intent, detailContactActivityRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == createContactActivityRequestCode && resultCode == Activity.RESULT_OK) {
            Timber.i("Intent received")
            intentData?.getParcelableExtra<ContactDatabase>("contact")?.let { reply ->
                val newContact = ContactDatabase(
                    firstName = reply.firstName,
                    lastName = reply.lastName,
                    mail = reply.mail
                )
                Timber.i("Intent received + " + newContact.toString())
                viewModel.insertContact(newContact)
            }
        } else if (requestCode == createContactActivityRequestCode && resultCode == Activity.RESULT_CANCELED) {

        }  else if (requestCode == detailContactActivityRequestCode && resultCode == Activity.RESULT_CANCELED) {

        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemClick(contact: Contact) {
        Timber.i("On Item clicked" + contact.toString())
        goToDetailContactActivity()
    }
}

