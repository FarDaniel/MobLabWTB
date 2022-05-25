package aut.moblab.wtb.ui.landing_dashboard

import aut.moblab.wtb.local_data.dao.MovieTagDao
import aut.moblab.wtb.local_data.models.MovieTag
import aut.moblab.wtb.network_data.interactors.TopMovieInteractor
import aut.moblab.wtb.network_data.models.MovieItems
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class LandingRepository @Inject constructor(
    val topMovieInteractor: TopMovieInteractor,
    val dao: MovieTagDao
) {

    fun getMovies(): MutableStateFlow<MovieItems> {
        val movieModels = MutableStateFlow(MovieItems(emptyList()))
        val backup = topMovieInteractor.getTopMovies().subscribe({ movies ->
            movieModels.value = movies
        }, {
            it.printStackTrace()
        })
        return movieModels
    }

    suspend fun addMovieTag(movieId: String, title: String) {
        dao.insertTag(
            MovieTag(
                movieId = movieId,
                title = title
            )
        )
    }

    suspend fun getMovieTags(): List<MovieTag> {
        return dao.getAllTag()
    }
}
