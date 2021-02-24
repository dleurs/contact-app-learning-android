package fr.dleurs.android.contactapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import fr.dleurs.android.contactapp.R
import fr.dleurs.android.contactapp.model.Contact
import fr.dleurs.android.contactapp.ui.SectionsPagerAdapter
import fr.dleurs.android.contactapp.utils.FabButtonInterface
import fr.dleurs.android.contactapp.viewmodel.ContactViewModel
import timber.log.Timber

class ContactActivity : AppCompatActivity(), FabButtonInterface {


    private lateinit var viewModel: ContactViewModel

    private val createContactActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ContactViewModel.Factory(this.application)
        ).get(ContactViewModel::class.java)


        setContentView(R.layout.contact_activity)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.contentFragment)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

    }

    override fun createContact() {
        Timber.i("Create a new contact started")
        val intent = Intent(this, CreateModifyContactActivity::class.java)
        startActivityForResult(intent, createContactActivityRequestCode)
    }
}

