package com.ltu.m7019e.mobile_app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
    when (newsListUiState) {
        is NewsListUiState.Success -> {
            LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = modifier) {
                items(newsListUiState.multipleNews) { news ->
                    NewsGridItemCard(
                        news = news,
                        onNewsListItemClick = onNewsListItemClick,
                        modifier = Modifier.padding(8.dp)
                    )
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
//        is NewsListUiState.NoInternet -> {
//            Column(
//                modifier = Modifier.fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.wifi_off_24px),
//                    contentDescription = "No wifi",
//                    modifier = Modifier.size(64.dp)
//                )
//
//                Text(
//                    text = "No Internet Connection!",
//                    style = MaterialTheme.typography.bodySmall,
//                    modifier = Modifier.padding(16.dp)
//                )
//            }
//        }
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
            .width(184.dp)
            .height(276.dp),
        onClick = {
            onNewsListItemClick(news)
        }
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = news.urlToImage,
                    contentDescription = news.title,
                    modifier = Modifier
                        .width(184.dp)
                        .height(276.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
