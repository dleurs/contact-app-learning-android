package fr.dleurs.android.contactapp.application

import android.app.Application
import fr.dleurs.android.contactapp.database.ContactsDatabase
import fr.dleurs.android.contactapp.database.getDatabase
import fr.dleurs.android.contactapp.repository.ContactRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ContactsApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { getDatabase(this) }
    val repository by lazy { ContactRepository(database) }
}