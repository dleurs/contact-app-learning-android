package fr.dleurs.android.contactapp.network

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import fr.dleurs.android.contactapp.database.ContactDatabase
import fr.dleurs.android.contactapp.database.ContactsDatabase
import fr.dleurs.android.contactapp.model.Contact
import kotlinx.android.parcel.Parcelize


@JsonClass(generateAdapter = true)
data class ContactNetworkContainer(val contacts: List<ContactNetwork>)


@JsonClass(generateAdapter = true)
data class ContactNetwork(
        val id: String,
        val firstName: String,
        val lastName: String,
        val mail: String)

fun ContactNetworkContainer.asDomainModel(): List<Contact> {
    return contacts.map {
        Contact(
                id = it.id,
                firstName = it.firstName,
                lastName = it.lastName,
                mail = it.mail
        )
    }
}

fun ContactNetworkContainer.asDatabaseModel(): List<ContactDatabase> {
    return contacts.map {
        ContactDatabase(
                id = it.id.toInt(),
                firstName = it.firstName,
                lastName = it.lastName,
                mail = it.mail)
    }
}
