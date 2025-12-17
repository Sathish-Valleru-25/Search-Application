package com.example.searchapplication.ui.theme.detailscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchapplication.data.model.Result
import com.example.searchapplication.data.repos.CharacterSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: CharacterSearchRepository
) : ViewModel() {

    private val characterId: Int =
        savedStateHandle["id"] ?:0

    val character: StateFlow<Result?> =
        repository.getCharacterFromCache(characterId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )
}
