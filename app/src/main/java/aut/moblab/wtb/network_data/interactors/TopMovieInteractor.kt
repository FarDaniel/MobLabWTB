package aut.moblab.wtb.network_data.interactors

import aut.moblab.wtb.network_data.apis.TopMovieApi
import aut.moblab.wtb.network_data.models.Movie
import aut.moblab.wtb.network_data.models.MovieItems
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TopMovieInteractor(val api: TopMovieApi) {
    fun getTopMovies(): Single<MovieItems> {
        return api.getTopMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    // Only here to mimic how to call delete
    fun deleteMovie(movieId: String) {
        api.deleteMovie(movieId)
    }

    // Only here to mimic how to call post
    fun addMovie(movie: Movie): Single<String> {
        return api.addMovie(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    // Only here to mimic how to call put
    fun updateMovie(movie: Movie) {
        api.updateMovie(movie)
    }
}