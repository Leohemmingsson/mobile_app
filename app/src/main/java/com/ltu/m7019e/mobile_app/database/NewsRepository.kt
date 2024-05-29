package com.ltu.m7019e.mobile_app.database

import com.ltu.m7019e.mobile_app.model.News
import com.ltu.m7019e.mobile_app.model.NewsListResponse
import com.ltu.m7019e.mobile_app.network.NewsApiService


interface NewsRepository {
    suspend fun getTopHeadlines(): NewsListResponse
}

class NetworkNewsRepository(private val apiService: NewsApiService ): NewsRepository{
    override suspend fun getTopHeadlines(): NewsListResponse {
        return apiService.getTopHeadlines()
    }

}
