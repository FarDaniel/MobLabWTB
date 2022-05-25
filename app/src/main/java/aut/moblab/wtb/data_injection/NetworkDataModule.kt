package aut.moblab.wtb.data_injection

import aut.moblab.wtb.network_data.apis.AwardApi
import aut.moblab.wtb.network_data.apis.MovieDetailsApi
import aut.moblab.wtb.network_data.apis.TopMovieApi
import aut.moblab.wtb.network_data.interactors.AwardInteractor
import aut.moblab.wtb.network_data.interactors.MovieDetailsInteractor
import aut.moblab.wtb.network_data.interactors.TopMovieInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkDataModule {

    @Provides
    @Singleton
    fun provideAwardInteractor(awardApi: AwardApi) = AwardInteractor(awardApi)

    @Provides
    @Singleton
    fun provideMovieDetailsInteractor(movieDetailsApi: MovieDetailsApi) =
        MovieDetailsInteractor(movieDetailsApi)

    @Provides
    @Singleton
    fun provideTopMovieInteractor(topMovieApi: TopMovieApi) = TopMovieInteractor(topMovieApi)

}
