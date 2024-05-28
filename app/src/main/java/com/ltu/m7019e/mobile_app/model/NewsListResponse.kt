package com.ltu.m7019e.mobile_app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsListResponse (
    @SerialName(value = "articles")
    var articles: List<News> = listOf(),

    @SerialName(value = "total_results")
    var total_results: Int = 0,
)