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
    var id: Long? = 0L,

    @SerialName(value = "source")
    var source: Source? = null,

    @SerialName(value = "author")
    var author: String? = null,

    @SerialName(value = "title")
    var title: String? = null,

    @SerialName(value = "description")
    var description: String? = null,

    @SerialName(value = "url")
    var url: String? = null,

    @SerialName(value = "urlToImage")
    var urlToImage: String? = null,

    @SerialName(value = "publishedAt")
    var publishedAt: String? = null,

    @SerialName(value = "content")
    var content: String? = null,

    var isSaved: Boolean = false
)

@Serializable
data class Source(
    val id: String? = null,
    val name: String
)