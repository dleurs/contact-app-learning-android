package fr.dleurs.android.contactapp.ui.detailsContact

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import fr.dleurs.android.contactapp.R
import fr.dleurs.android.contactapp.database.ContactDatabase
import fr.dleurs.android.contactapp.databinding.DetailsContactActivityBinding
import fr.dleurs.android.contactapp.model.Contact
import timber.log.Timber

class DetailsContactActivity : AppCompatActivity() {

    private lateinit var binding: DetailsContactActivityBinding
    private lateinit var contactPassed: Contact


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentData = intent.getParcelableExtra<Contact>("contact")?.let { reply ->
            contactPassed = Contact(
                id=reply.id,
                firstName = reply.firstName,
                lastName = reply.lastName,
                mail = reply.mail
            )
            Timber.i("Intent received + " + contactPassed.toString())
        }
        binding = DataBindingUtil.setContentView(this, R.layout.details_contact_activity)
        binding.apply { contact = contactPassed }
    }
}