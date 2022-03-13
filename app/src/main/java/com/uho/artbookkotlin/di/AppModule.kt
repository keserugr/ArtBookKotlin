package com.uho.artbookkotlin.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.uho.artbookkotlin.R
import com.uho.artbookkotlin.api.ArtRetrofitAPI
import com.uho.artbookkotlin.db.ArtDao
import com.uho.artbookkotlin.db.ArtDatabase
import com.uho.artbookkotlin.repo.ArtRepository
import com.uho.artbookkotlin.repo.IArtRepository
import com.uho.artbookkotlin.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectArtDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,ArtDatabase::class.java,"art_book"
    ).build()

    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase) = database.artDao()

    @Singleton
    @Provides
    fun injectRetrofitAPI(): ArtRetrofitAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ArtRetrofitAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectNormalRepo(dao: ArtDao, api: ArtRetrofitAPI) = ArtRepository(dao,api) as IArtRepository

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )
}