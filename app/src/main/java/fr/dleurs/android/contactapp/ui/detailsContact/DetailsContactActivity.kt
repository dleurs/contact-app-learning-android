package fr.dleurs.android.contactapp.ui.detailsContact

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import fr.dleurs.android.contactapp.R
import fr.dleurs.android.contactapp.database.ContactDatabase
import fr.dleurs.android.contactapp.database.asDomailModel
import fr.dleurs.android.contactapp.databinding.DetailsContactActivityBinding
import fr.dleurs.android.contactapp.model.Contact
import fr.dleurs.android.contactapp.model.asDatabaseModel
import fr.dleurs.android.contactapp.ui.main.createContactActivityRequestCode
import fr.dleurs.android.contactapp.ui.main.detailContactActivityRequestCode
import fr.dleurs.android.contactapp.ui.newModifyContact.CreateModifyContactActivity
import fr.dleurs.android.contactapp.viewmodel.ContactViewModel
import fr.dleurs.android.contactapp.viewmodel.ContactsViewModel
import fr.dleurs.android.contactapp.utils.OnClickDetailsScreen
import timber.log.Timber

class DetailsContactActivity : AppCompatActivity(), OnClickDetailsScreen {

    private lateinit var binding: DetailsContactActivityBinding
    private lateinit var contactId: String
    private lateinit var viewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.details_contact_activity)

        contactId = intent?.getStringExtra("contactId") ?: ""
        assert(!contactId.isNullOrEmpty())

        viewModel = ViewModelProvider(
            this,
            ContactViewModel.Factory(this.application)
        ).get(ContactViewModel::class.java)

        binding.onClickDetailsScreen = this
        viewModel.liveContact(contactId).observe(this, Observer<Contact> { theContact -> // this or viewLifecycleOwner ?
            theContact?.let {
                binding.apply { contact = it; }
            }
        })
    }

    override fun goToEditContact(contact: Contact) {
        val editIntent = Intent(this, CreateModifyContactActivity::class.java)
        editIntent.putExtra("contact", contact)
        startActivityForResult(editIntent, createContactActivityRequestCode)
    }

    override fun goBack() {
        val replyIntent = Intent()
        setResult(Activity.RESULT_CANCELED, replyIntent)
        finish()
    }

    override fun goDelete(contactId: String) {
        viewModel.deleteContact(contactId)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {

        if (requestCode == createContactActivityRequestCode && resultCode == Activity.RESULT_OK ) {
            val modifiedContact = intentData?.getParcelableExtra<ContactDatabase>("contact")
            Timber.i("modifiedContact : " + modifiedContact.toString())
            viewModel.updateContact(modifiedContact!!)
        }
        super.onActivityResult(requestCode, resultCode, intentData)
    }
}