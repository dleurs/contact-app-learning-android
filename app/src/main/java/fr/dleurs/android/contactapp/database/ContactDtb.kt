package fr.dleurs.android.contactapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.dleurs.android.contactapp.network.ContactNtw

@Entity(tableName = "contact_table")
data class ContactDtb(
        @PrimaryKey
        val id: Int = 0,
        val firstName: String,
        val lastName: String,
        val mail: String

) {
    //constructor(name: String) : this(0, name);
}

/**
 * Map DatabaseVideos to domain entities
 */
fun List<ContactDtb>.asDomainModel(): List<ContactNtw> {
    return map {
        ContactNtw(
                id = it.id,
                firstName = it.firstName,
                lastName = it.lastName,
                mail = it.mail,
        )
    }
}