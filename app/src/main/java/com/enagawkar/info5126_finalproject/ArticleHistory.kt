package com.enagawkar.info5126_finalproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enagawkar.info5126_finalproject.databinding.ActivityArticleHistoryBinding

class ArticleHistory : AppCompatActivity() {
    lateinit var recyclerViewManager: RecyclerView.LayoutManager
    lateinit var historyBinding: ActivityArticleHistoryBinding
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

        //historyBinding.recyclerView.adapter = RecyclerAdapter()
    }
}