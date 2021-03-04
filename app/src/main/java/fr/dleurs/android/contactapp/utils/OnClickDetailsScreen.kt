package fr.dleurs.android.contactapp.utils

import fr.dleurs.android.contactapp.model.Contact

interface OnClickDetailsScreen {
    fun goToEditContact(contact: Contact)
    fun goBack()
    fun goDelete(contactId: String)
}