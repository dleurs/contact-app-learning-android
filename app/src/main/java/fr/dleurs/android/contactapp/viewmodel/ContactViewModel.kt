package fr.dleurs.android.contactapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import fr.dleurs.android.contactapp.database.ContactDatabase
import fr.dleurs.android.contactapp.database.getDatabase
import fr.dleurs.android.contactapp.model.Contact
import fr.dleurs.android.contactapp.repository.ContactRepository
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private val contactsRepository = ContactRepository(getDatabase(application))
    fun liveContact(contactId: String): LiveData<Contact> = contactsRepository.contact(contactId)

    public fun updateContact(contact: ContactDatabase) {
        viewModelScope.launch {
            contactsRepository.updateContact(contact);
        }
    }

    public fun deleteContact(contactId: String) {
        viewModelScope.launch {
            contactsRepository.deleteContact(contactId);
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ContactViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}