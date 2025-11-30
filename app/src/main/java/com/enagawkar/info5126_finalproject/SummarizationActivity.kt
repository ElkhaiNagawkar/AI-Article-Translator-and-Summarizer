package com.enagawkar.info5126_finalproject

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.enagawkar.info5126_finalproject.databinding.ActivitySummarizationBinding
import com.enagawkar.info5126_finalproject.viewModel.MainViewModel
import com.google.mlkit.nl.languageid.LanguageIdentification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SummarizationActivity : AppCompatActivity() {
    lateinit var summarizationBinding: ActivitySummarizationBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        summarizationBinding = ActivitySummarizationBinding.inflate(layoutInflater)
        setContentView(summarizationBinding.root)
        //setContentView(R.layout.activity_summarization)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }
    fun onClickTransAndSum(view: View){
        mainViewModel.translateAndSummarize(
            summarizationBinding.artcileTitle.text.toString(),
            summarizationBinding.articleText.text.toString()
        )
    }

    fun nextAct(view: View){
        val intent = Intent(this, ArticleHistory::class.java)
        startActivity(intent)
    }
}