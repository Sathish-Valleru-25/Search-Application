package com.example.searchapplication.data.repos

import com.example.searchapplication.data.apiservice.CharacterSearchApiService
import com.example.searchapplication.data.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterSearchRepositoryImpl  @Inject constructor(private  val api: CharacterSearchApiService) : CharacterSearchRepository {

    private val cachedCharacter = mutableMapOf<Int, Result>()

    override suspend fun searchCharacters(name: String): List<Result> {
        val  response = api.getCharacterSearchResponse(name)

        cacheResults(response.results)
        return  response.results
    }

    private fun cacheResults(charactersList: List<Result>) {
        charactersList.forEach { result ->
            cachedCharacter[result.id] = result
        }
    }

    override fun getCharacterFromCache(id: Int): Flow<Result?> =
        flow { emit(cachedCharacter[id]) }

}