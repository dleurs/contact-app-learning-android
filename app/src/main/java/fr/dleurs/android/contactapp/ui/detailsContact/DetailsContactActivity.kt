package fr.dleurs.android.contactapp.ui.detailsContact

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import fr.dleurs.android.contactapp.R
import fr.dleurs.android.contactapp.database.ContactDatabase
import fr.dleurs.android.contactapp.databinding.DetailsContactActivityBinding
import fr.dleurs.android.contactapp.model.Contact
import fr.dleurs.android.contactapp.model.asDatabaseModel
import fr.dleurs.android.contactapp.ui.main.createContactActivityRequestCode
import fr.dleurs.android.contactapp.ui.main.detailContactActivityRequestCode
import fr.dleurs.android.contactapp.ui.newModifyContact.CreateModifyContactActivity
import timber.log.Timber

class DetailsContactActivity : AppCompatActivity() {

    private lateinit var binding: DetailsContactActivityBinding
    private lateinit var contactPassed: Contact


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val intentData = intent.getParcelableExtra<Contact>("contact")
        contactPassed = Contact(
            id = intentData!!.id,
            firstName = intentData!!.firstName,
            lastName = intentData!!.lastName,
            mail = intentData!!.mail
        )
        Timber.i("Intent received + " + contactPassed.toString())
        binding = DataBindingUtil.setContentView(this, R.layout.details_contact_activity)
        binding.apply { contact = contactPassed }

        val buttonBack = findViewById<ImageButton>(R.id.ibBack)
        buttonBack.setOnClickListener {
            val replyIntent = Intent()
            setResult(Activity.RESULT_CANCELED, replyIntent)
            finish()
        }

        val buttonDelete = findViewById<ImageButton>(R.id.ibDelete)
        buttonDelete.setOnClickListener {
            val deleteIntent = Intent()
            deleteIntent.putExtra("action", "delete")
            setResult(Activity.RESULT_OK, deleteIntent)
            finish()
        }

        val buttonEdit = findViewById<ImageButton>(R.id.ibEdit)
        buttonEdit.setOnClickListener {
            val editIntent = Intent(this, CreateModifyContactActivity::class.java)
            editIntent.putExtra("contact", contactPassed)
            startActivityForResult(editIntent, createContactActivityRequestCode)
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