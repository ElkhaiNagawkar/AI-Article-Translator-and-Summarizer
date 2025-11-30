package com.enagawkar.info5126_finalproject.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enagawkar.info5126_finalproject.model.ArticleData
import com.google.mlkit.nl.languageid.LanguageIdentification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    companion object ArticleObject {
        var listOfArticles = MutableLiveData<List<ArticleData>>(listOf<ArticleData>())

        fun addArticle(article: ArticleData){
            ArticleObject.listOfArticles.postValue(ArticleObject.listOfArticles.value?.toMutableList()?.apply { add(article) })
        }
    }

    var listOfArticles = ArticleObject.listOfArticles
    public fun translateAndSummarize(title: String, body: String) {
        var newArt: ArticleData? = null
        //languages codes
        var titleLanguage = ""
        var bodyLanguage = ""

        val languageIndetif = LanguageIdentification.getClient()
        languageIndetif.identifyLanguage(title)
            .addOnSuccessListener { languageCode ->
                if (languageCode == "und") {
                    println("cant")
                } else {
                    //This must be  done because this code is done asynchronously. (if langCode is declared outside, it does not get set in time and stays empty)
                    var langCode = languageCode
                    titleLanguage = langCode
                }
            }
            .addOnFailureListener {
                println("cant load")
            }

        languageIndetif.identifyLanguage(body)
            .addOnSuccessListener { languageCode ->
                if (languageCode == "und") {
                    println("cant")
                } else {
                    //This must be  done because this code is done asynchronously. (if langCode is declared outside, it does not get set in time and stays empty)
                    var langCode = languageCode
                    bodyLanguage = langCode
                }
            }.addOnFailureListener {
                println("cant load")
            }



    }
}

//                    newArt = ArticleData(languageCode, languageCode, languageCode)
//                    println(newArt)
//                    ArticleObject.addArticle(newArt)
//                    println(listOfArticles.value)
//titleLanguage = langCode