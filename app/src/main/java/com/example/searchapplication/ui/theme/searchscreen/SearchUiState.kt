package com.example.searchapplication.ui.theme.searchscreen

import com.example.searchapplication.data.model.Result

sealed interface SearchUiState {
    object Initial : SearchUiState
    object Loading : SearchUiState
    data class Success(val charactersSearchResponse: List<Result>) : SearchUiState
    data class Error(val errorMessage: String) : SearchUiState
}
