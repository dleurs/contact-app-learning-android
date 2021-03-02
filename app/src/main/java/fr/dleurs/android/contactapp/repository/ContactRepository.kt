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

class ContactRepository(private val contactDtbDao: ContactDtbDao) {

    val contacts: LiveData<List<Contact>> =
        Transformations.map(contactDtbDao.getContacts()) {
            it.asDomainModel()
        }

    fun contact(contactId: String): LiveData<Contact> =
        Transformations.map(contactDtbDao.getContact(contactId)) { it.asDomailModel() }


    suspend fun refreshContacts() {
        withContext(Dispatchers.IO) {
            Log.i("ContactRepo", "Refresh contacts is called");
            val contactList = ContactRetrofitApi.contacts.getContacts()
            Log.i("ContactRepo", "ContactList created ${contactList.toString()}");
            contactDtbDao.insertAll(contactList.asDatabaseModel())
        }
    }

    suspend fun insertContact(contact: ContactDatabase) {
        withContext(Dispatchers.IO) {
            contactDtbDao.insert(contact);
        }
    }

    suspend fun deleteContact(contactId: String) {
        withContext(Dispatchers.IO) {
            contactDtbDao.delete(contactId);
        }
    }

    suspend fun updateContact(contact: ContactDatabase) {
        withContext(Dispatchers.IO) {
            contactDtbDao.update(contact);
        }
    }
}