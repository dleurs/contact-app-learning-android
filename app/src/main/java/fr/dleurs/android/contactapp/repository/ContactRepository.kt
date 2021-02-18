package fr.dleurs.android.contactapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import fr.dleurs.android.contactapp.database.ContactDtbDao
import fr.dleurs.android.contactapp.database.ContactRetrofitApi
import fr.dleurs.android.contactapp.database.ContactsDatabase
import fr.dleurs.android.contactapp.database.asDomainModel
import fr.dleurs.android.contactapp.model.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import fr.dleurs.android.contactapp.network.asDatabaseModel

class ContactRepository(private val contactDtbDao: ContactDtbDao) {

    val contacts: LiveData<List<Contact>> = Transformations.map(contactDtbDao.getContacts()) {
        it.asDomainModel()
    }

    suspend fun refreshContacts() {
        withContext(Dispatchers.IO) {
            Timber.d("refresh contacts is called");
            val contactList = ContactRetrofitApi.contacts.getContacts()
            contactDtbDao.insertAll(contactList.asDatabaseModel())
        }
    }

}