package com.example.searchapplication.data.repos

import com.example.searchapplication.data.model.Result
import kotlinx.coroutines.flow.Flow

interface CharacterSearchRepository {

    suspend fun  searchCharacters(name: String) : List<Result>
    fun getCharacterFromCache(id: Int): Flow<Result?>
}