package com.enagawkar.info5126_finalproject

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.enagawkar.info5126_finalproject.databinding.ActivityTranslatedBinding

class TranslatedActivity : AppCompatActivity() {
    lateinit var translatedBinding: ActivityTranslatedBinding

    companion object{
        lateinit var articleTitle: String
        lateinit var articleBody: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        translatedBinding = ActivityTranslatedBinding.inflate(layoutInflater)
        setContentView(translatedBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        translatedBinding.articleTitleTextView.setText(articleTitle)
        translatedBinding.articleBodyTextView.setText(articleBody)

    }

    fun onClickExitButton(view: View) {
        finish()
    }
}