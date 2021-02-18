package fr.dleurs.android.contactapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

class ContactRoom {
}
@Dao
interface ContactDtbDao {
    @Query("select * from contact_table")
    fun getContacts(): LiveData<List<ContactDatabase>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( videos: List<ContactDatabase>)
}



@Database(entities = [ContactDatabase::class], version = 1)
abstract class ContactsDatabase: RoomDatabase() {
    abstract val contactDtbDao: ContactDtbDao
}

private lateinit var INSTANCE: ContactsDatabase

fun getDatabase(context: Context): ContactsDatabase {
    synchronized(ContactsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                    ContactsDatabase::class.java,
                    "contacts").build()
        }
    }
    return INSTANCE
}