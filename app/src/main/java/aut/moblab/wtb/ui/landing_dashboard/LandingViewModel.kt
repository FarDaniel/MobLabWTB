package aut.moblab.wtb.ui.landing_dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aut.moblab.wtb.ui.landing_dashboard.movies_recycler_view.MoviesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(private val repository: LandingRepository) :
    ViewModel() {

    val movies = MutableLiveData<ArrayList<MoviesModel>>()
    val taggedMovies = ArrayList<String>()

    fun addMovieTag(movieId: String, title: String) {
        viewModelScope.launch {
            repository.addMovieTag(movieId, title)
        }
    }

    fun update() {
        viewModelScope.launch {
            taggedMovies.clear()
            repository.getMovieTags().forEach {
                taggedMovies.add(it.movieId)
            }
            updateMovies()
        }
    }

    fun updateMovies() {
        viewModelScope.launch {
            movies.value?.clear()

            repository.getMovies().collect { movieItems ->
                val array = ArrayList<MoviesModel>()
                movieItems.movies.forEach { movie ->
                    if (!taggedMovies.contains(movie.movieId)) {
                        array.add(
                            MoviesModel(
                                title = movie.title,
                                image = movie.image,
                                movieId = movie.movieId
                            )
                        )
                    }
                }
                movies.value = array
            }
        }
    }
}
