package fr.dleurs.android.contactapp.ui

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputLayout
import fr.dleurs.android.contactapp.R
import fr.dleurs.android.contactapp.model.Contact
import fr.dleurs.android.contactapp.ui.SectionsPagerAdapter
import timber.log.Timber


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
            val replyIntent = Intent()
            //if (TextUtils.isEmpty(firs.text)) {
            //   setResult(Activity.RESULT_CANCELED, replyIntent)
            //} else {
            //val word = editWordView.text.toString()
            //replyIntent.putExtra(EXTRA_REPLY, word)
            Timber.i("Intent send")
            setResult(Activity.RESULT_OK, replyIntent)
            //}
            finish()
        }

        buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun validateEmailInput(): Boolean {
        var email: String = textInputMail.getEditText()!!.getText().toString().trim()
        if (email.isNullOrEmpty()) {
            textInputMail.setError("@string/new_contact_activity_error_form_empty")
            return false;
        }
        textInputMail.setError(null)
        return true;
    }

}