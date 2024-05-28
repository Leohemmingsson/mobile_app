package com.ltu.m7019e.mobile_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "saved_news")
data class News(
    @PrimaryKey
    @SerialName(value = "id")
    var id: Long = 0L,

    @SerialName(value = "source")
    var source: String = "",

    @SerialName(value = "author")
    var author: String,

    @SerialName(value = "title")
    var title: String,

    @SerialName(value = "description")
    var description: String,

    @SerialName(value = "url")
    var url: String = "",

    @SerialName(value = "urlToImage")
    var urlToImage: String = "",

    @SerialName(value = "published_at")
    var publishedAt: String,

    @SerialName(value = "content")
    var content: String = "",

    var isSaved: Boolean = false,

)
