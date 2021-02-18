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
    fun insertAll(videos: List<ContactDatabase>)
}


@Database(entities = [ContactDatabase::class], version = 1)
abstract class ContactsDatabase : RoomDatabase() {
    abstract val contactDtbDao: ContactDtbDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: ContactsDatabase

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
                        //populateDatabase(database.todoDao())
                    }
                }
            }
        }

/*        suspend fun populateDatabase(contactDao: ContactDtbDaoDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            todoDao.deleteAll()

            var word = TodoRoom("Do sport")
            todoDao.insert(word)
            word = TodoRoom("Meditation")
            todoDao.insert(word)
        }*/
    }
}



