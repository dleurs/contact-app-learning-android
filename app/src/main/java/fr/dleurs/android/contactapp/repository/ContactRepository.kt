package fr.dleurs.android.contactapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import fr.dleurs.android.contactapp.model.Contact
import fr.dleurs.android.contactapp.model.ContactRetrofitApi

class ContactRepository {

    val contacts: LiveData<List<Contact>> = Transformations.map(ContactRetrofitApi.retrofitService.getContacts()) {
        it.asDomainModel()
    }
}