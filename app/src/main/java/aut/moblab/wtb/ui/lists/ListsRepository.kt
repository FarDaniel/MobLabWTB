package aut.moblab.wtb.ui.lists

import aut.moblab.wtb.local_data.dao.MovieTagDao
import aut.moblab.wtb.local_data.models.MovieTag
import javax.inject.Inject

class ListsRepository @Inject constructor(val dao: MovieTagDao) {

    suspend fun getMovieTags(): List<MovieTag> {
        return dao.getAllTag()
    }

    suspend fun removeMovieTag(movieTag: MovieTag) {
        dao.deleteTag(movieTag.title)
    }

    suspend fun updateMovieTag(movieTag: MovieTag) {
        dao.insertTag(movieTag)
    }

}
