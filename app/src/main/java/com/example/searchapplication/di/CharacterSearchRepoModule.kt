package com.example.searchapplication.di

import com.example.searchapplication.data.repos.CharacterSearchRepository
import com.example.searchapplication.data.repos.CharacterSearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent :: class)
abstract  class CharacterSearchRepoModule {

    @Binds
    @Singleton
    abstract  fun getCharSearchRepo(characterSearchRepositoryImpl: CharacterSearchRepositoryImpl): CharacterSearchRepository

}