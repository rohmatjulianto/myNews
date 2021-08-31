package com.joule.mynews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.joule.mynews.data.model.Article
import com.joule.mynews.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val article = intent.getParcelableExtra<Article>(EXTRA_DATA)

        binding.webview.loadUrl(article?.url!!)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = article.title


    }

    companion object {
        val EXTRA_DATA = "data"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return false
    }

    override fun onBackPressed() {
        binding.webview.stopLoading()
        super.onBackPressed()
    }
}