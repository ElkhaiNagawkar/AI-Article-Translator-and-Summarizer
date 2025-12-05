package com.enagawkar.info5126_finalproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.enagawkar.info5126_finalproject.databinding.ActivityTranslationBinding
import com.enagawkar.info5126_finalproject.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TranslationActivity : AppCompatActivity() {
    lateinit var translationBinding: ActivityTranslationBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        translationBinding = ActivityTranslationBinding.inflate(layoutInflater)
        setContentView(translationBinding.root)
        //setContentView(R.layout.activity_summarization)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainViewModel.buttonToggle.observe(this){
            toggle ->
            translationBinding.transSummButton.isEnabled = toggle
        }

        mainViewModel.clearToggle.observe(this){
            toggle ->
            translationBinding.artcileTitle.setText("")
            translationBinding.articleText.setText("")
        }
    }
    fun onClickTranslate(view: View){
        CoroutineScope(Dispatchers.Default).launch {
            mainViewModel.translateArticle(
                translationBinding.artcileTitle.text.toString(),
                translationBinding.articleText.text.toString(),
                this@TranslationActivity
            )
        }
    }

    fun nextAct(view: View){
        val intent = Intent(this, ArticleHistory::class.java)
        startActivity(intent)
    }

    fun onClickBack(view: View){
        finish()
    }
}