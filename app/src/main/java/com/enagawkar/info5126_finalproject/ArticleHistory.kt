package com.enagawkar.info5126_finalproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enagawkar.info5126_finalproject.databinding.ActivityArticleHistoryBinding
import com.enagawkar.info5126_finalproject.model.ArticleData
import com.enagawkar.info5126_finalproject.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleHistory : AppCompatActivity(), RecyclerAdapter.RecyclerViewClickEvent {
    lateinit var recyclerViewManager: RecyclerView.LayoutManager
    lateinit var historyBinding: ActivityArticleHistoryBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var articleData: List<ArticleData>

    override fun onRowClick(position: Int) {
        val article = articleData[position]
        TranslatedActivity.articleTitle = article.title
        TranslatedActivity.articleBody = article.body
        val intent = Intent(this, TranslatedActivity::class.java).apply { }
        startActivity(intent)
        //Toast.makeText(this, article.title, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        historyBinding = ActivityArticleHistoryBinding.inflate(layoutInflater)
        setContentView(historyBinding.root)
        //setContentView(R.layout.activity_article_history)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerViewManager = LinearLayoutManager(applicationContext)
        historyBinding.recyclerView.setLayoutManager(recyclerViewManager)
        historyBinding.recyclerView.setHasFixedSize(true)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        articleData = listOf<ArticleData>()

        mainViewModel.listOfArticles.observe(this){
            articleData = it
            historyBinding.recyclerView.adapter = RecyclerAdapter(articleData, this)
        }

    }

    fun onClickExitButton(view: View) {
        finish()
    }
}