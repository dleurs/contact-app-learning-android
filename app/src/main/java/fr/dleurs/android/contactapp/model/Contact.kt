package fr.dleurs.android.contactapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact (
    val id: String,
    val firstName: String,
    val lastName: String,
    val mail: String) : Parcelable