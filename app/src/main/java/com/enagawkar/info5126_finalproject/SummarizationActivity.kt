package com.enagawkar.info5126_finalproject

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.enagawkar.info5126_finalproject.databinding.ActivitySummarizationBinding
import com.google.mlkit.nl.languageid.LanguageIdentification

class SummarizationActivity : AppCompatActivity() {
    lateinit var summarizationBinding: ActivitySummarizationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        summarizationBinding = ActivitySummarizationBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_summarization)
        //setContentView(R.layout.activity_summarization)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    fun onClickTransAndSum(view: View){
        val text = summarizationBinding.articleText.toString()
        val languageIndetif = LanguageIdentification.getClient()
        languageIndetif.identifyLanguage( text)
            .addOnSuccessListener { languageCode ->
                if(languageCode == "und"){
                    println("cant")
                }else {
                    val intent = Intent(this, ArticleHistory::class.java).apply {
                        putExtra("lang",languageCode)
                    }
                    startActivity(intent)
                }
            }
            .addOnFailureListener {
                println("cant load")
            }
    }
}