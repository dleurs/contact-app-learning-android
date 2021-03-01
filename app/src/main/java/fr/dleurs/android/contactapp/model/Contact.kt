package fr.dleurs.android.contactapp.model

import android.os.Parcelable
import fr.dleurs.android.contactapp.database.ContactDatabase
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    val id: String,
    val firstName: String,
    val lastName: String,
    val mail: String
) : Parcelable

fun Contact.asDatabaseModel(): ContactDatabase {
    return ContactDatabase(
        id = this.id.toInt(),
        firstName = this.firstName,
        lastName = this.lastName,
        mail = this.mail,
    )

}
