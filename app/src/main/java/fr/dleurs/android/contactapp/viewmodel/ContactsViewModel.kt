package fr.dleurs.android.contactapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import fr.dleurs.android.contactapp.database.ContactDatabase
import fr.dleurs.android.contactapp.database.ContactsDatabase
import fr.dleurs.android.contactapp.model.Contact
import fr.dleurs.android.contactapp.repository.ContactRepository
import kotlinx.coroutines.launch
import java.io.IOException

class ContactsViewModel(application: Application) : AndroidViewModel(application) {

    //private val contactsRepository = ContactRepository(ContactsDatabase.getDatabase(application))

    private val contactsRepository = ContactRepository(ContactsDatabase.getInstance(application).contactDtbDao())
    var liveContacts: LiveData<List<Contact>> = contactsRepository.contacts

    public fun insertContact(contact: ContactDatabase) {
        viewModelScope.launch {
            contactsRepository.insertContact(contact);
        }
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
            if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ContactsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
