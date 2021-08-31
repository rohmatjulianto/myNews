package com.joule.mynews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joule.mynews.adapter.NewsAdapter
import com.joule.mynews.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by inject()
    lateinit var binding: ActivityMainBinding
    var category: String = ""
    var search: String = ""
    var page: Int = 1;
    var pastVisible: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var loadingScroll: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val items = listOf(
            "business",
            "entertainment",
            "general",
            "health",
            "science",
            "sports",
            "technology"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        binding.etText.setAdapter(adapter)
        binding.etText.hint = "select category first!"
        binding.etText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.pbNews.visibility = View.VISIBLE
                category = s.toString()
                page = 1
                viewModel.getNewsCategory(category!!, page)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.search.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                binding.pbNews.visibility = View.VISIBLE
                page = 1
                viewModel.getSearchWithCategory(category, newText.toString(), page)
                return false
            }
        })


        val layoutList = LinearLayoutManager(this)
        binding.rvNews.layoutManager = layoutList
        viewModel.news.observe(this, {
            binding.pbNews.visibility = View.GONE
            binding.rvNews.adapter = NewsAdapter(it)
            if (totalItemCount != 0) {
                layoutList.scrollToPosition(totalItemCount)
            }

        })


        binding.rvNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = layoutList!!.childCount
                    totalItemCount = layoutList!!.itemCount
                    pastVisible = layoutList.findFirstVisibleItemPosition()
                    if (loadingScroll) {
                        if ((visibleItemCount + pastVisible) >= totalItemCount) {
                            loadingScroll = false
                            page = page + 1
                            binding.pbNews.visibility = View.VISIBLE
                            if (search.equals("")) {
                                viewModel.onLoadSearchCategory(category, search, page)
                            } else {
                                viewModel.onLoadCategory(category, page)
                            }
                            loadingScroll = true
                        }
                    }

                }
            }

        })
    }
}