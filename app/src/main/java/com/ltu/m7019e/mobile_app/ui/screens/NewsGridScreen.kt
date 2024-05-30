package com.ltu.m7019e.mobile_app.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ltu.m7019e.mobile_app.model.News
import com.ltu.m7019e.mobile_app.viewmodel.NewsListUiState

@Composable
fun NewsGridScreen(
    newsListUiState: NewsListUiState,
    onNewsListItemClick: (News) -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    when (newsListUiState) {
        is NewsListUiState.Success -> {
            if (isPortrait) {
                LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = modifier) {
                    items(newsListUiState.multipleNews) { news ->
                        NewsGridItemCard(
                            news = news,
                            onNewsListItemClick = onNewsListItemClick,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            } else {
                LazyColumn(modifier = modifier) {
                    items(newsListUiState.multipleNews) { news ->
                        NewsListItemCard(
                            news = news,
                            onNewsListItemClick = onNewsListItemClick,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
        is NewsListUiState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        is NewsListUiState.Error -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Error: Something went wrong!",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsGridItemCard(
    news: News,
    onNewsListItemClick: (News) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(200.dp)
            .height(250.dp),
        onClick = {
            onNewsListItemClick(news)
        }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentAlignment = Alignment.Center
            ) {
                if (news.urlToImage.isNullOrEmpty()) {
                    Text(
                        text = "No image available",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    AsyncImage(
                        model = news.urlToImage,
                        contentDescription = news.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            news.title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListItemCard(
    news: News,
    onNewsListItemClick: (News) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        onClick = {
            onNewsListItemClick(news)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                if (news.urlToImage.isNullOrEmpty()) {
                    Text(
                        text = "No image available",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    AsyncImage(
                        model = news.urlToImage,
                        contentDescription = news.title,
                        modifier = Modifier
                            .width(100.dp)
                            .height(120.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp)
            ) {
                news.title?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
