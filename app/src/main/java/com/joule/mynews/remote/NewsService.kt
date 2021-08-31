package com.joule.mynews.remote

import com.joule.mynews.data.model.Article
import com.joule.mynews.data.model.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService{
    @GET("/v2/top-headlines?country=us")
    suspend fun getNewsByCategory(@Query("category") category:String, @Query("page") page: Int) : BaseResponse<ArrayList<Article>>

    @GET("/v2/top-headlines?country=us")
    suspend fun getNewsByCategorySearch(@Query("category") category:String , @Query("q") value: String, @Query("page") page: Int) : BaseResponse<ArrayList<Article>>
}