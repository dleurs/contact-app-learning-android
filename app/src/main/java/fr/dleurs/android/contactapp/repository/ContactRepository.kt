package fr.dleurs.android.contactapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import fr.dleurs.android.contactapp.database.*
import fr.dleurs.android.contactapp.model.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import fr.dleurs.android.contactapp.network.asDatabaseModel
import java.util.logging.Level.INFO

class ContactRepository(private val database: ContactsDatabase) {

    val contacts: LiveData<List<Contact>> =
        Transformations.map(database.contactDtbDao.getContacts()) {
            it.asDomainModel()
        }

    suspend fun refreshContacts() {
        withContext(Dispatchers.IO) {
            Log.i("ContactRepo", "Refresh contacts is called");
            val contactList = ContactRetrofitApi.contacts.getContacts()
            Log.i("ContactRepo", "ContactList created ${contactList.toString()}");
            database.contactDtbDao.insertAll(contactList.asDatabaseModel())
        }
    }

    suspend fun insertContact(contact: ContactDatabase) {
        withContext(Dispatchers.IO) {
            database.contactDtbDao.insert(contact);
        }
    }

    suspend fun deleteContact(contact: ContactDatabase) {
        withContext(Dispatchers.IO) {
            database.contactDtbDao.delete(contact);
        }
    }

    suspend fun updateContact(contact: ContactDatabase) {
        withContext(Dispatchers.IO) {
            database.contactDtbDao.update(contact);
        }
    }
}