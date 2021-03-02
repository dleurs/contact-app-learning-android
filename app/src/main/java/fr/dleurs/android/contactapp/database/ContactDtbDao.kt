package fr.dleurs.android.contactapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Dao
interface ContactDtbDao {
    @Query("select * from contact_table")
    fun getContacts(): LiveData<List<ContactDatabase>>

    @Query("select * from contact_table WHERE id = :contactId")
    fun getContact(contactId: String): LiveData<ContactDatabase>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(contacts: List<ContactDatabase>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: ContactDatabase)

    @Query("DELETE FROM contact_table")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(contact: ContactDatabase)

    @Update
    suspend fun update(contact: ContactDatabase)

    @Query("UPDATE contact_table SET firstName = :firstName  & lastName = :lastname & mail = :mail WHERE id = :contactId ")
    suspend fun customUpdate(contactId: Int, firstName: String, lastname: String, mail: String)
}

@Database(entities = [ContactDatabase::class], version = 1)
abstract class ContactsDatabase : RoomDatabase() {
    abstract val contactDtbDao: ContactDtbDao
}

private lateinit var INSTANCE: ContactsDatabase

fun getDatabase(context: Context): ContactsDatabase {
    synchronized(ContactsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ContactsDatabase::class.java,
                "contacts"
            ).build()
        }
    }
    return INSTANCE
}



