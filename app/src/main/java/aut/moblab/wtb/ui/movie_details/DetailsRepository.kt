package aut.moblab.wtb.ui.movie_details

import aut.moblab.wtb.local_data.dao.MovieTagDao
import aut.moblab.wtb.local_data.models.MovieTag
import aut.moblab.wtb.network_data.interactors.AwardInteractor
import aut.moblab.wtb.network_data.interactors.MovieDetailsInteractor
import aut.moblab.wtb.network_data.models.AwardItems
import aut.moblab.wtb.network_data.models.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val detailsInteractor: MovieDetailsInteractor,
    private val awardInteractor: AwardInteractor,
    private val dao: MovieTagDao
) {

    fun getAwards(movieId: String): MutableStateFlow<AwardItems> {
        val awardItems = MutableStateFlow(AwardItems(emptyList(), ""))
        val backup = awardInteractor.getAwards(movieId).subscribe({ awards ->
            awardItems.value = awards
        }, {
            it.printStackTrace()
        })
        return awardItems
    }

    fun getMovieDetails(movieId: String): MutableStateFlow<Movie> {
        val movie = MutableStateFlow(
            Movie(
                movieId = "",
                title = "",
                image = "",
            )
        )
        val backup = detailsInteractor.getMovieDetails(movieId).subscribe({ details ->
            movie.value = details
        }, {
            it.printStackTrace()
        })
        return movie
    }

    suspend fun updateMovieTag(movieTag: MovieTag) {
        dao.deleteTag(movieTag.title)
        dao.insertTag(movieTag)
    }

    suspend fun getTagForMovie(movieId: String): MovieTag? {
        return dao.getTag(movieId)
    }

    suspend fun removeMovieTag(movieTitle: String) {
        dao.deleteTag(movieTitle)
    }

}
