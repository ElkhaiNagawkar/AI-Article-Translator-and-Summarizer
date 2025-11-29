package com.enagawkar.info5126_finalproject

import android.os.Bundle
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

class ArticleHistory : AppCompatActivity() {
    lateinit var recyclerViewManager: RecyclerView.LayoutManager
    lateinit var historyBinding: ActivityArticleHistoryBinding
    lateinit var mainViewModel: MainViewModel

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

        val list = mutableListOf<ArticleData>()
        list.add(ArticleData("ELKHAI", "", ""))
        var testAdapt = RecyclerAdapter(listOf())
        historyBinding.recyclerView.adapter = testAdapt

        mainViewModel.listOfArticles.observe(this){
            articleData ->
            testAdapt.updateUI(articleData)
        }

        //NEED TO ADD IN () THE DATA
        //historyBinding.recyclerView.adapter = RecyclerAdapter()
    }
}