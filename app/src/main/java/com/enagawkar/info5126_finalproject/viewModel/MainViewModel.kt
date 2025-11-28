package com.enagawkar.info5126_finalproject.viewModel

import android.content.Intent
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enagawkar.info5126_finalproject.ArticleHistory
import com.enagawkar.info5126_finalproject.model.ArticleData
import com.google.mlkit.nl.languageid.LanguageIdentification

class MainViewModel : ViewModel() {
    var listOfArticles = MutableLiveData<List<ArticleData>>(listOf<ArticleData>())
    var langCode = ""

    public fun translateAndSummarize(title: String, body: String) {
        //val tempList = listOfArticles.value!!.toMutableList()

        val languageIndetif = LanguageIdentification.getClient()
        languageIndetif.identifyLanguage(title)
            .addOnSuccessListener { languageCode ->
                if (languageCode == "und") {
                    println("cant")
                } else {
                    langCode = languageCode.toString()
                    listOfArticles.value = listOfArticles.value.orEmpty() + ArticleData(languageCode, languageCode, languageCode)
                    print(listOfArticles.value)
                }
            }
            .addOnFailureListener {
                println("cant load")
            }


    }
}