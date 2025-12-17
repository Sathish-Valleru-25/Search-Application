package com.example.searchapplication.ui.theme.searchscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.searchapplication.R
import com.example.searchapplication.data.model.Result
import com.example.searchapplication.ui.theme.navigation.Screen
import com.example.searchapplication.ui.theme.uicomponents.SearchbarUi


@Composable
fun SearchScreen(
    innerPadding: PaddingValues,
    navController: NavHostController,
) {

val viewModel: CharacterSearchVm = hiltViewModel()
    val searchUiState = viewModel.searchState.collectAsState()
    var name by rememberSaveable{
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(innerPadding)
    ){
        SearchbarUi(
            name =name,
            onNameChanged = {
                name = it
                viewModel.onQueryChanged(it)
            }
        )

        when ( searchUiState.value) {

            //Handle Initial State when search screen loads for the first time
            is SearchUiState.Initial -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        stringResource(R.string.search_info),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            //Handle Loading State
            is SearchUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()

                }
            }
            //Handle Success State
            is SearchUiState.Success -> {
                val charactersSearchResponse = (searchUiState.value as SearchUiState.Success).charactersSearchResponse
                CharacterListView(charactersSearchResponse,navController)

            }

            //Handle Error State
            is SearchUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(R.string.search_api_error))
                }

            }
        }
    }


}

@Composable
fun CharacterListView(
    results: List<Result>,
    navController: NavHostController,
) {

    if(results.isEmpty()) {
        Text(text = stringResource(R.string.search_empty))
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement =  Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items( count = results.size){
                CharacterCard(results[it],navController = navController)

            }

        }
    }

}



@Composable
fun CharacterCard(character: Result, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screen.detailRoute(character.id))
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            Text(
                text = character.name,
                modifier = Modifier
                    .padding(8.dp),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}




