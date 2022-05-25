package aut.moblab.wtb.network_data.interactors

import aut.moblab.wtb.network_data.apis.MovieDetailsApi
import aut.moblab.wtb.network_data.models.Movie
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailsInteractor(val api: MovieDetailsApi) {
    fun getMovieDetails(movieId: String): Single<Movie> {
        return api.getMovieDetails(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
