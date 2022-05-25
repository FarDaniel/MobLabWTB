package aut.moblab.wtb.data_injection

import aut.moblab.wtb.network_data.apis.AwardApi
import aut.moblab.wtb.network_data.apis.MovieDetailsApi
import aut.moblab.wtb.network_data.apis.TopMovieApi
import aut.moblab.wtb.network_data.interceptors.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://imdb-api.com/en/API/"

    @Provides
    fun provideGsonConverter() = GsonConverterFactory.create()

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
            .baseUrl(BASE_URL)
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
