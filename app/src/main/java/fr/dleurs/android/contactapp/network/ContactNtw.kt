package fr.dleurs.android.contactapp.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContactNtw (
        val id: Int,
        val firstName: String,
        val lastName: String,
        val mail: String) : Parcelable
