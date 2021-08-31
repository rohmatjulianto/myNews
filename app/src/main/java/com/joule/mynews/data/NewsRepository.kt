package com.joule.mynews.data

import android.util.Log
import com.joule.mynews.data.model.Article
import com.joule.mynews.data.model.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.lang.Exception

interface NewsRepository {
    suspend fun getByCategory(category: String, page: Int): Flow<BaseResponse<ArrayList<Article>>>
    suspend fun getByCategorySearch(
        category: String,
        value: String,
        page: Int
    ): Flow<BaseResponse<ArrayList<Article>>>
}

class NewsRepositoryImpl(private val dataSource: NewsDataSource) : NewsRepository {
    override suspend fun getByCategory(
        category: String,
        page: Int
    ): Flow<BaseResponse<ArrayList<Article>>> {
        val floww = flow {
            withContext(Dispatchers.IO) {
                try {
                    val result = dataSource.getByCategory(category, page)
                    emit(result)
                } catch (e: Exception) {
                    Log.d("yy", "getByCategorySearch: $e")
                }
            }
        }

        return floww
    }

    override suspend fun getByCategorySearch(
        category: String,
        value: String,
        page: Int
    ): Flow<BaseResponse<ArrayList<Article>>> {
        val floww = flow {
            withContext(Dispatchers.Main){
                try {
                    val result = dataSource.getByCategorySearch(category, value, page)
                    emit(result)
                }catch (e: Exception){
                    Log.d("yy", "getByCategorySearch: $e")
                }
            }
        }
        return floww
    }
}