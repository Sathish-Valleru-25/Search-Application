package com.example.searchapplication.ui.theme.searchscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchapplication.data.repos.CharacterSearchRepository
import com.example.searchapplication.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterSearchVm @Inject constructor(
    private val characterSearchRepository: CharacterSearchRepository,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _searchState = MutableStateFlow<SearchUiState>(SearchUiState.Initial)
    val searchState: StateFlow<SearchUiState> = _searchState

    private val searchFlow = MutableStateFlow("")

init {
    obServeSearchQuery()
}

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun obServeSearchQuery() {
        searchFlow
            .debounce(300)
            .distinctUntilChanged()
            .filter { it.isNotEmpty() }
            .flatMapLatest { query ->
                flow {
                    emit(SearchUiState.Loading)
                    val result = withContext(ioDispatcher) {
                        characterSearchRepository.searchCharacters(query)
                    }
                    emit(SearchUiState.Success(result))
                }
            }
            .catch {
                emit(SearchUiState.Error("Something went wrong"))
            }
            .onEach { state ->
                _searchState.value = state
            }
            .launchIn(viewModelScope)
    }

    fun onQueryChanged(query: String) {
        searchFlow.value = query
    }

}