package fr.dleurs.android.contactapp.application

import android.app.Application
import fr.dleurs.android.contactapp.database.ContactsDatabase
import fr.dleurs.android.contactapp.repository.ContactRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

class ContactsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}