package com.example.searchapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.searchapplication.data.model.Location
import com.example.searchapplication.data.model.Origin
import com.example.searchapplication.data.model.Result
import com.example.searchapplication.data.repos.CharacterSearchRepository
import com.example.searchapplication.ui.theme.searchscreen.CharacterSearchVm
import com.example.searchapplication.ui.theme.searchscreen.SearchUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


@OptIn(ExperimentalCoroutinesApi::class)
class CharacterSearchVmTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: CharacterSearchVm
    private  val mockRepository: CharacterSearchRepository = mock()

@Before
fun setup() {
    viewModel = CharacterSearchVm(mockRepository, mainCoroutineRule.dispatcher)
}

    @Test
    fun `initial state is Initial`() {
        Assert.assertTrue(viewModel.searchState.value is SearchUiState.Initial)
    }

    @Test
    fun `getCharactersResults with valid query should update state to Success`() = runTest {
        val query = "Ric"
        val episodes = listOf("Episode 1", "Episode 2")
        val fakeResult = Result(created = "", id = 1, name = "Rick Sanchez", status = "Alive", species = "Human", image = "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
            origin = Origin(name = "Earth (C-137)", url = "https://rickandmortyapi.com/api/location/1"), episode = episodes, location = Location(name = "Earth (C-137)", url = "https://rickandmortyapi.com/api/location/2"), type = "", url = ""
        , gender = "Male")
        val fakeResultsList = listOf(fakeResult)
        whenever(mockRepository.searchCharacters(query)).thenReturn(fakeResultsList)
        viewModel.onQueryChanged(query)
        advanceTimeBy(300)
        advanceUntilIdle()
        val state = viewModel.searchState.value
        Assert.assertTrue("State should be Success, but was ${state::class.simpleName}", state is SearchUiState.Success)
        Assert.assertEquals(fakeResultsList, (state as SearchUiState.Success).charactersSearchResponse)

    }
    @Test
    fun `getCharactersResults emits Error when repository throws`() = runTest {
        val name = "rickmmm"

        whenever(mockRepository.searchCharacters(name))
            .thenThrow(RuntimeException("Something went wrong"))

        viewModel.onQueryChanged(name)

        advanceTimeBy(300)
        advanceUntilIdle()
        val state = viewModel.searchState.value
        assert(state is SearchUiState.Error)
        Assert.assertEquals("Something went wrong", (state as SearchUiState.Error).errorMessage)
    }




}