package com.mamede.copa2022dadio.di

import com.mamede.copa2022dadio.data.repository.MatchesRepositoryImpl
import com.mamede.copa2022dadio.domain.repository.MatchesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class) // Essas instâncias vivem enquanto a ViewModel viver
abstract class DomainModule {

    @Binds
    abstract fun bindMatchesRepository(
        implementation: MatchesRepositoryImpl
    ): MatchesRepository
}