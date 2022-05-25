package aut.moblab.wtb.ui.movie_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aut.moblab.wtb.local_data.models.MovieTag
import aut.moblab.wtb.network_data.models.Movie
import aut.moblab.wtb.ui.movie_details.award_recycler_view.AwardModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: DetailsRepository) :
    ViewModel() {
    val awardModels = MutableLiveData<List<AwardModel>>()
    val movie = MutableLiveData<Movie>()
    val movieTag = MutableLiveData<MovieTag>()
    val hasSeen = MutableLiveData(false)

    fun loadAwards(movieId: String) {
        viewModelScope.launch {
            val awards = ArrayList<AwardModel>()
            repository.getAwards(movieId).collect { awardItems ->
                awardItems.awards.forEach { awardEvent ->
                    awardEvent.details.forEach { actualAward ->
                        val title =
                            actualAward.category + " (${actualAward.outcome}) " + awardEvent.year
                        var description = ""
                        actualAward.details.forEach {
                            description += it.description + "\n\n"
                        }
                        awards.add(
                            AwardModel(
                                title = title,
                                description = description
                            )
                        )
                    }
                }
                awardModels.value = awards
            }
        }
    }

    fun loadMovie(movieId: String) {
        viewModelScope.launch {
            repository.getMovieDetails(movieId).collect {
                movie.value = it
                val tag = repository.getTagForMovie(movieId)
                if (tag != null) {
                    hasSeen.value = true
                    movieTag.value = tag
                } else {
                    hasSeen.value = false
                    movieTag.value = MovieTag(
                        movieId = movieId,
                        title = it.title
                    )
                }
            }
        }
    }

    fun favouriteClicked() {
        viewModelScope.launch {
            hasSeen.value = true
            val tag = movieTag.value
            tag?.let {
                val newTag = MovieTag(
                    id = tag.id,
                    movieId = tag.movieId,
                    title = tag.title,
                    isFavourite = !tag.isFavourite,
                    isBlackListed = false
                )
                repository.updateMovieTag(newTag)
                movieTag.value = newTag
            }
        }
    }

    fun blackListClicked() {
        viewModelScope.launch {
            hasSeen.value = true
            val tag = movieTag.value
            tag?.let {
                val newTag = MovieTag(
                    id = tag.id,
                    movieId = tag.movieId,
                    title = tag.title,
                    isFavourite = false,
                    isBlackListed = !tag.isBlackListed
                )
                repository.updateMovieTag(newTag)
                movieTag.value = newTag
            }
        }
    }

    fun seenClicked() {
        viewModelScope.launch {
            val seen = hasSeen.value == false
            hasSeen.value = seen
            if (seen) {
                movieTag.value?.let { movieTag ->
                    repository.updateMovieTag(movieTag)
                }
            } else {
                movieTag.value?.let { tag ->
                    repository.removeMovieTag(tag.title)
                    val newTag = MovieTag(
                        id = tag.id,
                        movieId = tag.movieId,
                        title = tag.title,
                        isFavourite = false,
                        isBlackListed = false
                    )
                    movieTag.value = newTag
                }
            }
        }
    }

}
