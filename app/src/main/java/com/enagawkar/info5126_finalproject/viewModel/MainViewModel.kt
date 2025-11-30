package com.enagawkar.info5126_finalproject.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enagawkar.info5126_finalproject.model.ArticleData
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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

        val languageIndetif = LanguageIdentification.getClient()
        languageIndetif.identifyLanguage(title)
            .addOnSuccessListener { languageCode ->
                if (languageCode == "und") {
                    println("cant")
                }
                else
                {
                    val options = TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.fromLanguageTag(languageCode)!!)
                        .setTargetLanguage(TranslateLanguage.ENGLISH)
                        .build()
                    val Translator = Translation.getClient(options)

                    var conditions = DownloadConditions.Builder()
                        .requireWifi()
                        .build()
                    Translator.downloadModelIfNeeded(conditions)
                        .addOnSuccessListener {
                            println("downloaded")
                        }
                        .addOnFailureListener { exception ->
                            println("Can't Download")
                        }

                    Translator.translate(title).addOnSuccessListener {
                            translatedTitle ->
                        newArt = ArticleData(translatedTitle, "", "")
                            ArticleObject.addArticle(newArt)
                    }
                }
            }
            .addOnFailureListener {
                println("cant load")
            }



//        if(titleLanguage != "en" || bodyLanguage != "en"){

//        }

    }
}

//newArt = ArticleData(titleLanguage, "", "")
//ArticleObject.addArticle(newArt)




