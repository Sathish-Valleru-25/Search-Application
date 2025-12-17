package com.example.searchapplication.ui.theme.detailscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.searchapplication.R
import com.example.searchapplication.data.model.Result

@Composable
fun CharacterDetailScreen (){
      val  detailViewModel : DetailViewModel = hiltViewModel()
   val  character by detailViewModel.character.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        character?.let { char ->
            CharacterDetailsContent(character = char)
        } ?: run {
           Text(text = "No Character details found ")
        }
    }

}


@Composable
private fun CharacterDetailsContent(character: Result) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 48.dp)
    ) {
        Text(
            text = character.name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        AsyncImage(
            model = character.image,
            contentDescription = character.name,

            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Fit
        )

        Spacer(Modifier.height(16.dp))

        Text(stringResource(R.string.status, character.status))
        Text(stringResource(R.string.species, character.species))
        Text(stringResource(R.string.origin, character.origin.name))
        if (character.type.isNotEmpty()) {
            Text(stringResource(R.string.type, character.type))
        }
    }
}