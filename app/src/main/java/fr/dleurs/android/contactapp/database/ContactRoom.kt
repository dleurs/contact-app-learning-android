package fr.dleurs.android.contactapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactRoom {
}

@Dao
interface ContactDtbDao {
    @Query("select * from contact_table")
    fun getContacts(): LiveData<List<ContactDatabase>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(contacts: List<ContactDatabase>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contact: ContactDatabase)

    @Query("DELETE FROM contact_table")
    suspend fun deleteAll()
}


@Database(entities = [ContactDatabase::class], version = 1)
abstract class ContactsDatabase : RoomDatabase() {

    abstract fun contactDtbDao(): ContactDtbDao

    companion object {
        @Volatile
        private var INSTANCE: ContactsDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ContactsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactsDatabase::class.java,
                    "todo_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(TodoDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class TodoDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.contactDtbDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(contactDao: ContactDtbDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            contactDao.deleteAll()

            var word = ContactDatabase(firstName = "Dimitr", lastName = "Leurs", mail = "di@le.com")
            contactDao.insert(word)
        }
    }
}



