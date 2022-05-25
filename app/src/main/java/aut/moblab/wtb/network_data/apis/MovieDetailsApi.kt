package aut.moblab.wtb.network_data.apis

import aut.moblab.wtb.network_data.models.Movie
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailsApi {
    companion object {
        private const val BASE_PATH = "Title"
        private const val KEY = "$BASE_PATH/{api_key}"
        private const val MOVIE_ID = "$KEY/{id}"
    }

    @GET(MOVIE_ID)
    fun getMovieDetails(
        @Path("id") movieId: String
    ): Single<Movie>
}