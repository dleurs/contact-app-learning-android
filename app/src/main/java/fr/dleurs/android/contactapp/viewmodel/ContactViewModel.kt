package fr.dleurs.android.contactapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import fr.dleurs.android.contactapp.database.ContactDatabase
import fr.dleurs.android.contactapp.database.getDatabase
import fr.dleurs.android.contactapp.model.Contact
import fr.dleurs.android.contactapp.repository.ContactRepository
import kotlinx.coroutines.launch
import java.io.IOException

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    //private val contactsRepository = ContactRepository(ContactsDatabase.getDatabase(application))

    private val contactsRepository = ContactRepository(getDatabase(application))
    var liveContacts: LiveData<List<Contact>> = contactsRepository.contacts
    private val _navigateToSelectedContact: MutableLiveData<Contact?> = MutableLiveData<Contact?>()
    val navigateToSelectedContact: LiveData<Contact?>
        get() = _navigateToSelectedContact

    public fun insertContact(contact: ContactDatabase) {
        viewModelScope.launch {
            contactsRepository.insertContact(contact);
        }
    }

    fun displayContactDetails(contact: Contact) {
        _navigateToSelectedContact.value = contact;
    }

    fun displayContactDetailsComplete() {
        _navigateToSelectedContact.value = null
    }

    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     */
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    /**
     * Flag to display the error message. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    /**
     * Flag to display the error message. Views should use this to get access
     * to the data.
     */
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    /**
     * init{} is called immediately when this ViewModel is created.
     */
    init {
        refreshDataFromRepository()
    }

    /**
     * Refresh data from the repository. Use a coroutine launch to run in a
     * background thread.
     */
    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                contactsRepository.refreshContacts()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                Log.i("ContactViewModel", "Error on refreshContact : ${networkError.toString()}")
                if (liveContacts.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    /**
     * Resets the network error flag.
     */
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ContactViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
