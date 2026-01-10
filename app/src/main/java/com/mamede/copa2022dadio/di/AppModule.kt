package com.mamede.copa2022dadio.di

import android.app.Application
import androidx.room.Room
import com.mamede.copa2022dadio.MatchesApi
import com.mamede.copa2022dadio.data.local.AppDataBase
import com.mamede.copa2022dadio.data.local.dao.MatchesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // 1. Ensinar a criar o Retrofit (API)
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://digitalinnovationone.github.io/copa-2022-android/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 2. Ensinar a criar a interface da API
    @Provides
    @Singleton
    fun provideMatchesApi(retrofit: Retrofit): MatchesApi {
        return retrofit.create(MatchesApi::class.java)
    }

    // 3. Ensinar a criar o Banco de Dados (Room)
    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDataBase {
        return Room.databaseBuilder(
            application,
            AppDataBase::class.java,
            "copa2022.db"
        ).fallbackToDestructiveMigration() // Limpa o banco se mudarmos a versão
            .build()
    }

    // 4. Ensinar a criar o DAO
    @Provides
    @Singleton
    fun provideMatchesDao(database: AppDataBase): MatchesDao {
        return database.matchesDao()
    }
}