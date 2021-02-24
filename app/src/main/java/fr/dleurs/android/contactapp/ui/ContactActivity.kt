package fr.dleurs.android.contactapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
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
import fr.dleurs.android.contactapp.viewmodel.ContactViewModel

class ContactActivity : AppCompatActivity() {

    private val viewModel: ContactViewModel by lazy {
        ViewModelProvider(this, ContactViewModel.Factory(this.application))
            .get(ContactViewModel::class.java)
    }

    private val contactAdapter: ContactAdapter = ContactAdapter()

    /**
     * Called when the activity is starting.  This is where most initialization
     * should go
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.liveContacts.observe(this, Observer<List<Contact>> { contacts -> // this or viewLifecycleOwner ?
            contacts?.let {
                contactAdapter.contacts = it
            }
        })

        setContentView(R.layout.contact_activity)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.contentFragment)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

    }
}

