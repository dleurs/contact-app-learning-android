package fr.dleurs.android.contactapp.ui

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import fr.dleurs.android.contactapp.R
import fr.dleurs.android.contactapp.model.Contact
import fr.dleurs.android.contactapp.ui.SectionsPagerAdapter


class CreateModifyContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.create_modify_contact_activity)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            //if (TextUtils.isEmpty(firs.text)) {
            //   setResult(Activity.RESULT_CANCELED, replyIntent)
            //} else {
            //val word = editWordView.text.toString()
            //replyIntent.putExtra(EXTRA_REPLY, word)
            setResult(Activity.RESULT_OK, replyIntent)
            //}
            finish()
        }
    }
}