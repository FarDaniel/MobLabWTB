package aut.moblab.wtb.network_data.apis

import aut.moblab.wtb.network_data.models.AwardItems
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface AwardApi {
    companion object {
        private const val BASE_PATH = "Awards"
        private const val KEY = "$BASE_PATH/{api_key}"
        private const val MOVIE_ID = "$KEY/{id}"
    }

    @GET(MOVIE_ID)
    fun getAwards(
        @Path("id") movieId: String
    ): Single<AwardItems>
}
