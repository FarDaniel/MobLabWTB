package aut.moblab.wtb

import androidx.test.ext.junit.runners.AndroidJUnit4
import aut.moblab.wtb.data_injection.NetworkModule
import aut.moblab.wtb.network_data.apis.AwardApi
import aut.moblab.wtb.network_data.apis.MovieDetailsApi
import aut.moblab.wtb.network_data.apis.TopMovieApi
import aut.moblab.wtb.network_data.interactors.AwardInteractor
import aut.moblab.wtb.network_data.interactors.MovieDetailsInteractor
import aut.moblab.wtb.network_data.interactors.TopMovieInteractor
import aut.moblab.wtb.network_data.interceptors.AuthInterceptor
import aut.moblab.wtb.ui.landing_dashboard.LandingRepository
import aut.moblab.wtb.ui.landing_dashboard.LandingViewModel
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Thread.sleep
import javax.inject.Inject
import javax.inject.Singleton


@UninstallModules(NetworkModule::class)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ApiTests {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var topMovieInteractor: TopMovieInteractor

    @Inject
    lateinit var awardInteractor: AwardInteractor

    @Inject
    lateinit var movieDetailsInteractor: MovieDetailsInteractor

    @Inject
    lateinit var landingRepository: LandingRepository


    companion object {
        lateinit var mockWebServer: MockWebServer
        lateinit var baseUrl: String
        private const val TEST_API_KEY = "k_jqru4qvo"

        val dispatcher: Dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                when (request.path) {
                    "/test/Top250Movies/$TEST_API_KEY" -> return MockResponse()
                        .setResponseCode(200)
                        .setBody(AssetReader.getResponse("top_movies.json"))
                    "/test/Title/$TEST_API_KEY/tt0468569" -> return MockResponse()
                        .setResponseCode(200)
                        .setBody(AssetReader.getResponse("batman_details.json"))
                    "/test/Awards/$TEST_API_KEY/tt0468569" -> return MockResponse()
                        .setResponseCode(200)
                        .setBody(AssetReader.getResponse("batman_awards.json"))
                }
                return MockResponse().setResponseCode(404)
            }
        }

        @Module
        @InstallIn(SingletonComponent::class)
        class NetworkModule {

            @Provides
            fun provideGsonConverter() = GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            )

            @Provides
            fun provideCallAdapter() = RxJava2CallAdapterFactory.create()

            @Provides
            fun provideAuthInterceptor() =
                AuthInterceptor()

            @Provides
            @Singleton
            fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
                return OkHttpClient()
                    .newBuilder()
                    .addInterceptor(authInterceptor)
                    .build()
            }

            @Provides
            @Singleton
            fun provideRetrofit(
                converter: GsonConverterFactory,
                callAdapterFactory: RxJava2CallAdapterFactory,
                okHttpClient: OkHttpClient
            ): Retrofit {
                return Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(converter)
                    .addCallAdapterFactory(callAdapterFactory)
                    .client(okHttpClient)
                    .build()
            }

            @Provides
            @Singleton
            fun provideAwardApi(retrofit: Retrofit): AwardApi {
                return retrofit.create(AwardApi::class.java)
            }

            @Provides
            @Singleton
            fun provideMovieDetailsApi(retrofit: Retrofit): MovieDetailsApi {
                return retrofit.create(MovieDetailsApi::class.java)
            }

            @Provides
            @Singleton
            fun provideTopMovieApi(retrofit: Retrofit): TopMovieApi {
                return retrofit.create(TopMovieApi::class.java)
            }
        }

        @BeforeClass
        @JvmStatic
        fun setup() {

            mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcher
            mockWebServer.start()

            baseUrl = mockWebServer.url("test").toString() + "/"

        }
    }

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun get_top_movies_test() {
        var answered = false
        val callback = topMovieInteractor.getTopMovies().subscribe({ movies ->
            assertEquals(6, movies.movies.size)
            assertEquals("The Shawshank Redemption", movies.movies[0].title)
            assertEquals("The Dark Knight", movies.movies[2].title)
            answered = true
        }, {
            throw it
        })
        while (!answered) {
            sleep(1000)
        }
    }

    @Test
    fun get_awards_for_batman_test() {
        var answered = false
        val callback = awardInteractor.getAwards("tt0468569").subscribe({ awardItems ->
            assertEquals(110, awardItems.awards.size)
            assertEquals("Showing all 159 wins and 163 nominations", awardItems.description)
            assertEquals(2009, awardItems.awards[0].year)
            assertEquals(
                "Best Performance by an Actor in a Supporting Role Heath Ledger " +
                        "Posthumously. The award was accepted by his father, mother and sister. " +
                        "Ledger is the only actor to ... More Posthumously. The award was " +
                        "accepted by his father, mother and sister. Ledger is the only actor to " +
                        "have won an award in this category after death.",
                awardItems.awards[0].details[0].details[0].description
            )
            answered = true
        }, {
            throw it
        })
        while (!answered) {
            sleep(1000)
        }
    }

    @Test
    fun get_batman_details() {
        var answered = false
        val callback = movieDetailsInteractor.getMovieDetails("tt0468569").subscribe({ movie ->
            assertEquals("The Dark Knight", movie.title)
            assertEquals(
                "https://imdb-api.com/images/original/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFt" +
                        "ZTcwODAyMTk2Mw@@._V1_Ratio0.6751_AL_.jpg",
                movie.image
            )
            assertEquals(2008, movie.year)
            answered = true
        }, {
            throw it
        })
        while (!answered) {
            sleep(1000)
        }
    }

    @Test
    fun landing_viewmodel_test() {
        var answered = false
        val viewModel = LandingViewModel(landingRepository)
        viewModel.taggedMovies.clear()
        viewModel.taggedMovies.add("tt0468569")
        viewModel.updateMovies()
        GlobalScope.launch(Dispatchers.Main) {
            viewModel.movies.observeForever {
                if (it.size != 0) {
                    assertEquals(5, it.size)
                    answered = true
                }
            }
        }
        while (!answered) {
            sleep(1000)
        }
    }
}
