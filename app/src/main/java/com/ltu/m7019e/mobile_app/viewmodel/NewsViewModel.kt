package com.ltu.m7019e.mobile_app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ltu.m7019e.mobile_app.NewsApplication
import com.ltu.m7019e.mobile_app.database.NewsRepository
import com.ltu.m7019e.mobile_app.database.UserRepository
import com.ltu.m7019e.mobile_app.model.News
import com.ltu.m7019e.mobile_app.model.User
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface NewsListUiState {
    data class Success(val multipleNews: List<News>) : NewsListUiState
    object Error : NewsListUiState
    object Loading : NewsListUiState
}

sealed interface SelectedNewsUiState {
    data class Success(val news: News) : SelectedNewsUiState
    object Error : SelectedNewsUiState
    object Loading : SelectedNewsUiState
}

class NewsViewModel(
    private val newsRepository: NewsRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    var newsListUiState: NewsListUiState by mutableStateOf(NewsListUiState.Loading)
        private set
    var selectedNewsUiState: SelectedNewsUiState by mutableStateOf(SelectedNewsUiState.Loading)
        private set

    var loggedIn: Boolean by mutableStateOf(false)
        private set

    init {
        getTopHeadlines()
    }

    fun getTopHeadlines() {
        viewModelScope.launch {
            newsListUiState = NewsListUiState.Loading
            newsListUiState = try {
                NewsListUiState.Success(newsRepository.getTopHeadlines().results)
            } catch (e: IOException) {
                NewsListUiState.Error
            } catch (e: HttpException) {
                NewsListUiState.Error
            }
        }
    }

    fun setSelectedNews(news: News) {
        selectedNewsUiState = SelectedNewsUiState.Loading
        viewModelScope.launch {
            selectedNewsUiState = try {
                SelectedNewsUiState.Success(news = news)
            } catch (e: IOException) {
                SelectedNewsUiState.Error
            } catch (e: HttpException) {
                SelectedNewsUiState.Error
            }
        }
    }

    suspend fun login(username: String, password: String): Boolean {
        return try {
            val success = userRepository.login(username, password)
            loggedIn = success
            success
        } catch (e: Exception) {
            false
        }
    }

    fun logout() {
        loggedIn = false
    }

    fun registerUser(username: String, password: String, country: String) {
        val newUser = User(username = username, password = password, country = country)
        viewModelScope.launch {
            userRepository.register(newUser)
        }
    }

    fun getNewsById(newsId: Long): News? {
        return (newsListUiState as? NewsListUiState.Success)?.multipleNews?.find { it.id == newsId }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NewsApplication)
                val newsRepository = application.container.newsRepository
                val userRepository = application.container.userRepository
                NewsViewModel(newsRepository = newsRepository, userRepository = userRepository)
            }
        }
    }
}
