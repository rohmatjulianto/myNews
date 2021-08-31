package com.joule.mynews.data

import com.joule.mynews.remote.NewsService

class NewsDataSource(private val newsService: NewsService) {
    suspend fun getByCategory(category: String, page: Int) = newsService.getNewsByCategory(category, page)
    suspend fun getByCategorySearch(category: String, value: String, page: Int) = newsService.getNewsByCategorySearch(category, value, page)
    fun getFailure(msg : String) : String = msg
}