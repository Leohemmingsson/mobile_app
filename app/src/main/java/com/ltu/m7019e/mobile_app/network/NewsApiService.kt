package com.ltu.m7019e.mobile_app.network


import com.ltu.m7019e.mobile_app.model.News
import com.ltu.m7019e.mobile_app.model.NewsListResponse
import com.ltu.m7019e.mobile_app.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiService {
    @GET("top")
    suspend fun getTopHeadlines (
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): NewsListResponse

    @GET("{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id")
        movie_id: String,
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): Movie

    @GET("{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id")
        movie_id: String,
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): ReviewListResponse

    @GET("{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id")
        movie_id: String,
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): MovieVideoListResponse

}


}