package fr.dleurs.android.contactapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.dleurs.android.contactapp.R


class ContactActivity : AppCompatActivity() {

    /**
     * Called when the activity is starting.  This is where most initialization
     * should go
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_activity)
    }
}

