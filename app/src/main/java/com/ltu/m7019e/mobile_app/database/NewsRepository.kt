package com.ltu.m7019e.mobile_app.database

import com.ltu.m7019e.mobile_app.model.NewsListResponse
import com.ltu.m7019e.mobile_app.network.NewsApiService

interface NewsRepository {
    suspend fun getTopHeadlines(country: String): NewsListResponse
    suspend fun getEverything(): NewsListResponse
}

class NetworkNewsRepository(private val apiService: NewsApiService): NewsRepository {
    override suspend fun getTopHeadlines(country: String): NewsListResponse {
        return apiService.getTopHeadlines(country = country)
    }

    override suspend fun getEverything(): NewsListResponse {
        return apiService.getEverything()
    }
}
