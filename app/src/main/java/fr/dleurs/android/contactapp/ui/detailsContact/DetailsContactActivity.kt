package fr.dleurs.android.contactapp.ui.detailsContact

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.dleurs.android.contactapp.R
import fr.dleurs.android.contactapp.database.ContactDatabase
import fr.dleurs.android.contactapp.model.Contact
import timber.log.Timber

class DetailsContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentData = intent.getParcelableExtra<Contact>("contact")?.let { reply ->
            val contact = ContactDatabase(
                firstName = reply.firstName,
                lastName = reply.lastName,
                mail = reply.mail
            )
            Timber.i("Intent received + " + contact.toString())
        }

        setContentView(R.layout.details_contact_activity)


    }
}