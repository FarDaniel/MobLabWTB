package aut.moblab.wtb.data_injection

import android.content.Context
import aut.moblab.wtb.local_data.database.MovieTagDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideDao(@ApplicationContext context: Context) =
        MovieTagDatabase.getInstance(context).movieTagDao()

}
