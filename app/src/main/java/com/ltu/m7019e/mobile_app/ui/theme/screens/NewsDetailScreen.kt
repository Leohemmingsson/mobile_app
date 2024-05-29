package com.ltu.m7019e.mobile_app.ui.theme.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

import com.ltu.m7019e.mobile_app.viewmodel.NewsViewModel
import com.ltu.m7019e.mobile_app.viewmodel.SelectedNewsUiState

@Composable
fun NewsDetailScreen(
    newsUiState: SelectedNewsUiState,
    modifier: Modifier = Modifier
) {
    when(newsUiState) {
        is SelectedNewsUiState.Success -> {
            Column(
                modifier = modifier.fillMaxSize()
            ) {
                Text(
                    text = newsUiState.news.title ?: "Title not available",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = newsUiState.news.content ?: "Description not available",
                    style = MaterialTheme.typography.bodyMedium
                )
                Box (modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    AsyncImage(
                        model = newsUiState.news.urlToImage,
                        contentDescription = newsUiState.news.title,
                        modifier = modifier
                            .width(184.dp)
                            .height(276.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                // Add more details as needed
            }
        }
        is SelectedNewsUiState.Loading -> {
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }
        is SelectedNewsUiState.Error -> {
            Text(
                text = "Error: Something went wrong!",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }
    }


}
