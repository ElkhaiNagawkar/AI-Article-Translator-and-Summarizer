package com.enagawkar.info5126_finalproject.viewModel

import android.content.Intent
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enagawkar.info5126_finalproject.ArticleHistory
import com.enagawkar.info5126_finalproject.model.ArticleData
import com.google.mlkit.nl.languageid.LanguageIdentification

class MainViewModel : ViewModel() {

    companion object ArticleObject {
        var listOfArticles = MutableLiveData<List<ArticleData>>(listOf<ArticleData>())

        fun addArticle(article: ArticleData){
            ArticleObject.listOfArticles.postValue(ArticleObject.listOfArticles.value?.toMutableList()?.apply { add(article) })
        }
    }

    var listOfArticles = ArticleObject.listOfArticles
    var langCode = ""

    public fun translateAndSummarize(title: String, body: String) {
        val tempList = listOfArticles.value!!.toMutableList()
        var newArt: ArticleData? = null

        val languageIndetif = LanguageIdentification.getClient()
        languageIndetif.identifyLanguage(title)
            .addOnSuccessListener { languageCode ->
                if (languageCode == "und") {
                    println("cant")
                } else {
                    langCode = languageCode.toString()
                    newArt = ArticleData(languageCode, languageCode, languageCode)
                    println(newArt)
                    ArticleObject.addArticle(newArt)
                    println(listOfArticles.value)

                }
            }
            .addOnFailureListener {
                println("cant load")
            }
    }
}