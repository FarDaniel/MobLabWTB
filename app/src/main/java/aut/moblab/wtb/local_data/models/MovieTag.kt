package aut.moblab.wtb.local_data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieTag(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val movieId: String,
    val title: String,
    val isFavourite: Boolean = false,
    val isBlackListed: Boolean = false
)