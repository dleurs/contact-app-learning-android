package fr.dleurs.android.contactapp.ui.detailsContact

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fr.dleurs.android.contactapp.R
import fr.dleurs.android.contactapp.database.ContactDatabase
import fr.dleurs.android.contactapp.databinding.DetailsContactActivityBinding
import fr.dleurs.android.contactapp.model.Contact
import fr.dleurs.android.contactapp.model.asDatabaseModel
import fr.dleurs.android.contactapp.ui.main.createContactActivityRequestCode
import fr.dleurs.android.contactapp.ui.main.detailContactActivityRequestCode
import fr.dleurs.android.contactapp.ui.newModifyContact.CreateModifyContactActivity
import fr.dleurs.android.contactapp.viewmodel.ContactViewModel
import fr.dleurs.android.contactapp.viewmodel.ContactsViewModel
import timber.log.Timber

class DetailsContactActivity : AppCompatActivity() {

    private lateinit var binding: DetailsContactActivityBinding
    private lateinit var contactId: String
    private lateinit var viewModel: ContactViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contactId = intent?.getStringExtra("contactId") ?: ""
        assert(!contactId.isNullOrEmpty())

        viewModel = ViewModelProvider(
            this,
            ContactViewModel.Factory(this.application)
        ).get(ContactViewModel::class.java)

        val buttonEdit = findViewById<ImageButton>(R.id.ibEdit)
        val buttonDelete = findViewById<ImageButton>(R.id.ibDelete)
        val buttonBack = findViewById<ImageButton>(R.id.ibBack)

        binding = DataBindingUtil.setContentView(this, R.layout.details_contact_activity)
        viewModel.liveContact(contactId).observe(this, Observer<Contact> { theContact -> // this or viewLifecycleOwner ?
            theContact?.let {
                binding.apply { contact = it }
            }
        })

        buttonEdit.setOnClickListener {
            val editIntent = Intent(this, CreateModifyContactActivity::class.java)
            //editIntent.putExtra("contact", it)
            startActivityForResult(editIntent, createContactActivityRequestCode)
        }

        buttonDelete.setOnClickListener {
            viewModel.deleteContact(contactId)
            finish()
        }


        buttonBack.setOnClickListener {
            val replyIntent = Intent()
            setResult(Activity.RESULT_CANCELED, replyIntent)
            finish()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {

        if (requestCode == createContactActivityRequestCode && resultCode == Activity.RESULT_OK ) {
            val modifiedIntent = Intent()
            val modifiedContact = intentData?.getParcelableExtra<ContactDatabase>("contact")
            Timber.i("modifiedContact : " + modifiedContact.toString())
            val contact: Contact = Contact(
                id = modifiedContact!!.id.toString(),
                firstName = modifiedContact!!.firstName,
                lastName = modifiedContact!!.lastName,
                mail = modifiedContact!!.mail
            )
            modifiedIntent.putExtra("action", "modify")
            modifiedIntent.putExtra("contact", contact)
            setResult(Activity.RESULT_OK, modifiedIntent)
            finish()
        }
        super.onActivityResult(requestCode, resultCode, intentData)
    }
}