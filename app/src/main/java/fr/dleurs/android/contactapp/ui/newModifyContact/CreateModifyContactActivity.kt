package fr.dleurs.android.contactapp.ui.newModifyContact

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout
import fr.dleurs.android.contactapp.R
import fr.dleurs.android.contactapp.database.ContactDatabase
import timber.log.Timber
import java.util.regex.Pattern


class CreateModifyContactActivity : AppCompatActivity() {

    private lateinit var textInputLastName: TextInputLayout
    private lateinit var textInputFirstName: TextInputLayout
    private lateinit var textInputMail: TextInputLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.create_modify_contact_activity)

        val buttonSave = findViewById<Button>(R.id.button_save)
        val buttonBack = findViewById<ImageButton>(R.id.ibBack)
        textInputLastName = findViewById(R.id.tiLastName)
        textInputFirstName = findViewById(R.id.tiFirstName)
        textInputMail = findViewById(R.id.tiMail)

        buttonSave.setOnClickListener {
            if (validateInput()) {
                val replyIntent = Intent()
                val contact: ContactDatabase = ContactDatabase(firstName = getFirstName(), lastName = getLastName(), mail = getEmail())
                replyIntent.putExtra("contact", contact)
                Timber.i("Intent send : " + contact.toString())
                setResult(Activity.RESULT_OK, replyIntent)
                finish()
            }
        }

        buttonBack.setOnClickListener {
            finish()
        }

        textInputMail.editText?.doOnTextChanged{ inputText, _, _, _ ->
            textInputMail.setError(null)
        }
        textInputLastName.editText?.doOnTextChanged{ inputText, _, _, _ ->
            textInputLastName.setError(null)
        }
        textInputFirstName.editText?.doOnTextChanged{ inputText, _, _, _ ->
            textInputFirstName.setError(null)
        }
    }

    private fun getEmail():String {
        return textInputMail.getEditText()?.getText().toString().trim() ?: "";
    }

    private fun getLastName():String {
        return textInputLastName.getEditText()?.getText().toString().trim() ?: "";
    }

    private fun getFirstName():String {
        return textInputFirstName.getEditText()?.getText().toString().trim() ?: "";
    }

    private fun validateEmailInput(): Boolean {
        var email: String = getEmail()
        if (email.isNullOrEmpty()) {
            textInputMail.setError(getText(R.string.new_contact_activity_error_form_empty))
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {//(Pattern.compile("[a-zA-Z0-9._%-+]{1,256}@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(.[a-zA-Z0-9][a-zA-Z0-9-]{0,25})+").matcher(email).matches()) {
            textInputMail.setError(getText(R.string.new_contact_activity_error_form_valid_email))
            return false;
        }
        textInputMail.setError(null)
        return true;
    }



    private fun validateLastNameInput(): Boolean {
        var lastname: String = getLastName()
        if (lastname.isNullOrEmpty()) {
            textInputLastName.setError(getText(R.string.new_contact_activity_error_form_empty))
            return false;
        }
        else if (!Pattern.compile("^[A-zÀ-ú- ]+$").matcher(lastname).matches()) {
            textInputLastName.setError(getText(R.string.new_contact_activity_error_form_valid_name))
            return false;
        }
        textInputLastName.setError(null)
        return true;
    }

    private fun validateFirstNameInput(): Boolean {
        var firstname: String = getFirstName()
        if (firstname.isNullOrEmpty()) {
            textInputFirstName.setError(getText(R.string.new_contact_activity_error_form_empty))
            return false;
        }
        else if (!Pattern.compile("^[A-zÀ-ú- ]+$").matcher(firstname).matches()) {
            textInputFirstName.setError(getText(R.string.new_contact_activity_error_form_valid_name))
            return false;
        }
        textInputFirstName.setError(null)
        return true;
    }

    private fun validateInput(): Boolean {
        val resEmail: Boolean = validateEmailInput()
        val resLastName: Boolean = validateLastNameInput()
        val resFirstName: Boolean =  validateFirstNameInput()
        if (!resEmail || !resLastName || !resFirstName ) { // Cannot place function directly because || operator is lazy, not all functions (and errors) will be executed
            return false;
        }
        Timber.i("Validated")
        return true;
    }
}
