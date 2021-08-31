package com.joule.mynews.data.model

data class BaseResponse<T>(val status: String, val totalResults: Int, val articles: T? = null)
