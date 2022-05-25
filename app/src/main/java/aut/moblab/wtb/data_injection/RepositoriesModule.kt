package aut.moblab.wtb.data_injection

import aut.moblab.wtb.local_data.dao.MovieTagDao
import aut.moblab.wtb.network_data.interactors.AwardInteractor
import aut.moblab.wtb.network_data.interactors.MovieDetailsInteractor
import aut.moblab.wtb.network_data.interactors.TopMovieInteractor
import aut.moblab.wtb.ui.landing_dashboard.LandingRepository
import aut.moblab.wtb.ui.lists.ListsRepository
import aut.moblab.wtb.ui.movie_details.DetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    @Singleton
    fun provideLandingRepository(topMovieInteractor: TopMovieInteractor, dao: MovieTagDao) =
        LandingRepository(topMovieInteractor, dao)

    @Provides
    @Singleton
    fun provideDetailsRepository(
        detailsInteractor: MovieDetailsInteractor,
        awardInteractor: AwardInteractor,
        dao: MovieTagDao
    ) =
        DetailsRepository(detailsInteractor, awardInteractor, dao)

    @Provides
    @Singleton
    fun provideListsRepository(dao: MovieTagDao) =
        ListsRepository(dao)
}