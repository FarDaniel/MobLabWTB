package aut.moblab.wtb.local_data.dao

import androidx.room.*
import aut.moblab.wtb.local_data.models.MovieTag

@Dao
interface MovieTagDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(movieTag: MovieTag): Long

    @Query("SELECT * FROM MovieTag")
    suspend fun getAllTag(): List<MovieTag>

    @Transaction
    @Query("SELECT * FROM MovieTag WHERE movieId = :movieId")
    suspend fun getTag(movieId: String): MovieTag

    @Query("DELETE FROM MovieTag WHERE title = :movieTitle")
    suspend fun deleteTag(movieTitle: String)

    @Query("DELETE FROM MovieTag")
    suspend fun deleteAll()

}