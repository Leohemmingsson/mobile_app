package com.ltu.m7019e.mobile_app

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ltu.m7019e.mobile_app.ui.theme.screens.NewsDetailScreen
import com.ltu.m7019e.mobile_app.ui.theme.screens.NewsGridScreen
import com.ltu.m7019e.mobile_app.viewmodel.NewsViewModel


enum class NewsScreen(@StringRes val title: Int) {
    List(title = R.string.app_name),
    Detail(title = R.string.news_details),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsAppBar(
    currentScreen: NewsScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    newsViewModel: NewsViewModel
) {
    var menuExpanded by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        actions = {
            if (currentScreen == NewsScreen.List) {
                IconButton(onClick = {
                    menuExpanded = !menuExpanded
                }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Open menu to select different news lists"
                    )

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.topnews)) },
                            onClick = {
                                newsViewModel.getTopHeadlines()

                                menuExpanded = false
                            })

                        DropdownMenuItem(text = {
                            Text(stringResource(R.string.detailednews)) // change to otherscrren later
                        }, onClick = {
                            newsViewModel.getTopHeadlines() // change to otherscrren later

                            menuExpanded = false
                        })

                        DropdownMenuItem(text = {
                            Text(stringResource(R.string.saved_news)) // change to otherscrren later
                        }, onClick = {
                            newsViewModel.getTopHeadlines() //change to saved news later

                            menuExpanded = false
                        })
                    }

                }
            }
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }

    )
}


@Composable
fun TheNewsApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

        val currentScreen = NewsScreen.valueOf(
        backStackEntry?.destination?.route ?: NewsScreen.List.name
    )

    val newsViewModel : NewsViewModel = viewModel(factory = NewsViewModel.Factory)

    Scaffold (
        topBar = {
            NewsAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                newsViewModel = newsViewModel
            )
        }
    ) {innerPadding ->

        NavHost(
            navController = navController,
            startDestination = NewsScreen.List.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = NewsScreen.List.name) {
                // MovieListScreen(
                NewsGridScreen(
                    newsListUiState = newsViewModel.newsListUiState,
                    onNewsListItemClick  = {news ->

                        newsViewModel.setSelectedNews(news)
                        navController.navigate(NewsScreen.Detail.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }

            composable(route = NewsScreen.Detail.name) {
                NewsDetailScreen(
                    newsUiState = newsViewModel.selectedNewsUiState,
                    modifier = Modifier
                )
            }


        }
    }
}