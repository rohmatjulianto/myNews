package com.joule.mynews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joule.mynews.data.NewsRepository
import com.joule.mynews.data.model.Article
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(val repo: NewsRepository) : ViewModel() {
    val _news = MutableLiveData<ArrayList<Article>>()
    val news: LiveData<ArrayList<Article>> = _news

    fun getNewsCategory(category: String, page: Int) {
        viewModelScope.launch {
            val result = repo.getByCategory(category, page)
            result.collect {
                if (it.status.equals("ok")) {
                    _news.postValue(it.articles)
                }
            }
        }
    }

    fun onLoadCategory(category: String, page: Int) {
        viewModelScope.launch {
            val pastResult: ArrayList<Article> = _news.value!!
            val result = repo.getByCategory(category, page)
            result.collect {
                if (it.articles != null) {
                    pastResult.addAll(it.articles!!)
                    _news.postValue(pastResult)
                }
            }
        }
    }

    fun getSearchWithCategory(category: String, value: String, page: Int) {
        viewModelScope.launch {
            val result = repo.getByCategorySearch(category, value, page)
            result.collect {
                if (it.status.equals("ok")) {
                    _news.postValue(it.articles)
                } else {
                    failure()
                }
            }
        }
    }

    fun onLoadSearchCategory(category: String, value: String, page: Int) {
        viewModelScope.launch {
            val pastResult: ArrayList<Article> = _news.value!!
            val result = repo.getByCategorySearch(category, value, page)
            result.collect {
                if (it.articles != null) {
                    pastResult.addAll(it.articles!!)
                    _news.postValue(pastResult)
                } else {
                    failure()
                }
            }


        }
    }

    fun failure() {
        Log.d("yy", "failure: ")
    }
}