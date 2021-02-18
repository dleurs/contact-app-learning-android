package fr.dleurs.android.contactapp.repository

import fr.dleurs.android.contactapp.database.Contact
import fr.dleurs.android.contactapp.database.ContactRetrofitApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ContactRepository {

    //val contacts: LiveData<List<Contact>>;
    //val contacts: MutableLiveData<List<Contact>> = MutableLiveData<List<Contact>>()
    val contacts: MutableList<Contact> = mutableListOf<Contact>()

    suspend fun refreshContacts() {
        withContext(Dispatchers.IO) {
            Timber.d("refresh contacts is called");
            val contactsNetwork = ContactRetrofitApi.retrofitService.getContacts()
            contacts.addAll(contactsNetwork)
        }
    }

}