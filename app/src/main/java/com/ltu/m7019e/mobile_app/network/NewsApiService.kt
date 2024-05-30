package com.ltu.m7019e.mobile_app.network


import com.ltu.m7019e.mobile_app.model.News
import com.ltu.m7019e.mobile_app.model.NewsListResponse
import com.ltu.m7019e.mobile_app.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines (
        @Query("apiKey")
        apiKey: String = Constants.API_KEY,
        @Query("country")
        country: String

    ): NewsListResponse

    @GET("everything")
    suspend fun getEverything (
        @Query("apiKey")
        apiKey: String = Constants.API_KEY,
        @Query("q")
        q: String = "language=en"

    ): NewsListResponse

}