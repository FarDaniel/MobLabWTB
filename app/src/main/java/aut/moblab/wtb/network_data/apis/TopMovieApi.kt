package aut.moblab.wtb.network_data.apis

import aut.moblab.wtb.network_data.models.Movie
import aut.moblab.wtb.network_data.models.MovieItems
import io.reactivex.Single
import retrofit2.http.*

interface TopMovieApi {
    companion object {
        private const val BASE_PATH = "Top250Movies"
        private const val KEY = "$BASE_PATH/{api_key}"

        // Only for demonstration
        private const val MOVIE_ID = "$BASE_PATH/{id}"
    }

    @GET(KEY)
    fun getTopMovies(): Single<MovieItems>

    // Only here to mimic how to call delete
    @DELETE(MOVIE_ID)
    fun deleteMovie(
        @Path("id") userId: String
    )

    // Only here to mimic how to call post
    @POST(KEY)
    fun addMovie(movie: Movie): Single<String>

    // Only here to mimic how to call put
    @PUT(KEY)
    fun updateMovie(
        @Body() movie: Movie
    )
}