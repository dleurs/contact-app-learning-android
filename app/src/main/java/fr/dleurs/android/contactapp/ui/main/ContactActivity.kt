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
import fr.dleurs.android.contactapp.model.asDatabaseModel
import fr.dleurs.android.contactapp.ui.detailsContact.DetailsContactActivity
import fr.dleurs.android.contactapp.ui.newModifyContact.CreateModifyContactActivity
import fr.dleurs.android.contactapp.utils.FabButtonInterface
import fr.dleurs.android.contactapp.viewmodel.ContactsViewModel
import timber.log.Timber

public val createContactActivityRequestCode = 1
public val detailContactActivityRequestCode = 2


class ContactActivity : AppCompatActivity(), FabButtonInterface, OnClick {


    private lateinit var viewModel: ContactsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ContactsViewModel.Factory(this.application)
        ).get(ContactsViewModel::class.java)


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

    fun goToDetailContactActivity(contactId: String) {
        Timber.i("Details contact started")
        val intent = Intent(this, DetailsContactActivity::class.java)
        intent.putExtra("contactId", contactId)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == createContactActivityRequestCode && resultCode == Activity.RESULT_OK) {
            Timber.i("Intent received")
            val response = intentData?.getParcelableExtra<ContactDatabase>("contact")
            val newContact = ContactDatabase(
                firstName = response!!.firstName,
                lastName = response!!.lastName,
                mail = response!!.mail
            )
            Timber.i("Intent received + " + newContact.toString())
            viewModel.insertContact(newContact)

        } else if (requestCode == createContactActivityRequestCode && resultCode == Activity.RESULT_CANCELED) {

        } else if (requestCode == detailContactActivityRequestCode) {
            Timber.i("DetailsContactActivity intent :" + intent.toString() + ", intentData: "+intentData.toString()+"resultCode :" + resultCode.toString())
            if (resultCode == Activity.RESULT_CANCELED) {
                Timber.i("DetailsContactActivity closed with no action")
            }

        } else {
            Toast.makeText(this, "Error creating contact", Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemClick(contactId: String) {
        Timber.i("On Item clicked + " + contactId)
        goToDetailContactActivity(contactId)
    }
}

