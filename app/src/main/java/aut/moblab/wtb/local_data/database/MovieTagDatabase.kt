package aut.moblab.wtb.local_data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import aut.moblab.wtb.local_data.dao.MovieTagDao
import aut.moblab.wtb.local_data.models.MovieTag

@Database(
    entities = [MovieTag::class],
    version = 1
)
abstract class MovieTagDatabase : RoomDatabase() {

    abstract fun movieTagDao(): MovieTagDao

    companion object {
        @Volatile
        private var INSTANCE: MovieTagDatabase? = null

        fun getInstance(context: Context): MovieTagDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MovieTagDatabase::class.java,
                    "WordPack_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}