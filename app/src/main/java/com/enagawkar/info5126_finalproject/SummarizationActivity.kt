package com.enagawkar.info5126_finalproject

import android.os.Bundle
import android.text.InputFilter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.enagawkar.info5126_finalproject.databinding.ActivitySummarizationBinding

class SummarizationActivity : AppCompatActivity() {
    lateinit var summariazationBinding: ActivitySummarizationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        summariazationBinding = ActivitySummarizationBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_summarization)
        //setContentView(R.layout.activity_summarization)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}