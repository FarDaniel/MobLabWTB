package aut.moblab.wtb.ui.lists

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aut.moblab.wtb.local_data.models.MovieTag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(private val repository: ListsRepository) :
    ViewModel(), MovieTagRefreshener {
    val watchedMovies = MutableLiveData<List<MovieTag>>()
    val blackListedMovies = MutableLiveData<List<MovieTag>>()

    init {
        refreshLists()
    }

    fun refreshLists() {
        viewModelScope.launch {
            val allTag = repository.getMovieTags()
            val favourite = ArrayList<MovieTag>()
            val watched = ArrayList<MovieTag>()
            val blackList = ArrayList<MovieTag>()
            allTag.forEach { tag ->
                when {
                    tag.isBlackListed -> {
                        blackList.add(tag)
                    }
                    tag.isFavourite -> {
                        favourite.add(tag)
                    }
                    else -> {
                        watched.add(tag)
                    }
                }
            }

            // This way favourite movies are prior in line.
            favourite.addAll(watched)
            watchedMovies.value = favourite
            blackListedMovies.value = blackList
        }
    }

    fun removeTag(removed: MovieTag) {
        viewModelScope.launch {
            repository.removeMovieTag(removed)
        }
    }

    override fun refreshTag(movieTag: MovieTag) {
        viewModelScope.launch {
            repository.updateMovieTag(movieTag)
        }
    }
}
