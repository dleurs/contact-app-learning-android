package fr.dleurs.android.contactapp.utils

import android.app.Activity
import android.content.Intent
import androidx.core.app.ActivityCompat.startActivityForResult
import fr.dleurs.android.contactapp.ui.newModifyContact.CreateModifyContactActivity
import timber.log.Timber


interface FabButtonInterface {

    fun goToCreateContactActivity()
    fun goToDetailContactActivity()
}