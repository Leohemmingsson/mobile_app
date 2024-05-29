package com.ltu.m7019e.mobile_app.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ltu.m7019e.mobile_app.R
import com.ltu.m7019e.mobile_app.viewmodel.SelectedNewsUiState

@Composable
fun NewsDetailScreen(
    newsUiState: SelectedNewsUiState,
    modifier: Modifier = Modifier
) {

    when(newsUiState) {
        is SelectedNewsUiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = newsUiState.news.title ?: "Title not available",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Published: ${newsUiState.news.publishedAt ?: "Date not available"}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Source:  ${newsUiState.news.source?.name ?: "Source not available"}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                    Text(
                        text = "Author: ${newsUiState.news.author ?: "Author not available"}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(276.dp)
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = newsUiState.news.urlToImage,
                        contentDescription = newsUiState.news.title,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }

                Text(
                    text = newsUiState.news.content ?: "Content not available",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                newsUiState.news.url?.let {
                    LinkButton(
                        buttonText = R.string.website_button,
                        url = it,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
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

@Composable
fun LinkButton(
    @StringRes buttonText: Int,
    url: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    if (url.isNotEmpty()) {
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
                context.startActivity(intent)
            },
            modifier = modifier.padding(8.dp)
        ) {
            Text(stringResource(buttonText))
        }
    }
}