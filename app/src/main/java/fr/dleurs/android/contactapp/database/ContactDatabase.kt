package fr.dleurs.android.contactapp.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.dleurs.android.contactapp.model.Contact
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "contact_table")
data class ContactDatabase(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val mail: String
) : Parcelable

/**
 * Map DatabaseVideos to domain entities
 */
fun List<ContactDatabase>.asDomainModel(): List<Contact> {
    return map {
        Contact(
            id = it.id.toString(),
            firstName = it.firstName,
            lastName = it.lastName,
            mail = it.mail,
        )
    }
}

fun ContactDatabase.asDomailModel(): Contact {
    return Contact(
        id = this.id.toString(),
        firstName = this.firstName,
        lastName = this.lastName,
        mail = this.mail,
    )
}