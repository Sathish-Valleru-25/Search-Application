package com.example.searchapplication.data.apiservice

import com.example.searchapplication.data.model.CharactersSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterSearchApiService {

    @GET("character")
     suspend fun getCharacterSearchResponse(@Query("name") name: String) : CharactersSearchResponse
}