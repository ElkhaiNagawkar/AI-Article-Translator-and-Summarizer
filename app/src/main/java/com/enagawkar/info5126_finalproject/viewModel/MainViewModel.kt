package com.enagawkar.info5126_finalproject.viewModel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enagawkar.info5126_finalproject.ArticleHistory
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

    //need to make a companion object (singleton) so that the same view model is used on all activities
    companion object ArticleObject {
        var listOfArticles = MutableLiveData<List<ArticleData>>(listOf<ArticleData>())

        fun addArticle(article: ArticleData){
            ArticleObject.listOfArticles.postValue(ArticleObject.listOfArticles.value?.toMutableList()?.apply { add(article) })
        }
    }

    var listOfArticles = ArticleObject.listOfArticles
    var newArt: ArticleData? = null


    public fun translateAndSummarize(title: String, body: String) {

        val languageIndetif = LanguageIdentification.getClient()
        languageIndetif.identifyLanguage(title)
            .addOnSuccessListener { languageCode ->
                if (languageCode == "und") {
                    println("cant")
                }
                else
                {
                    newArt = translateTitleAndBody(title, body, languageCode)
                    ArticleObject.addArticle( newArt!!)
                }
            }
            .addOnFailureListener {
                println("cant load")
            }


    }

    private fun translateTitleAndBody(title: String, body: String, langCode: String): ArticleData{
        var articleToAdd: ArticleData? = ArticleData("", "", "")

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.fromLanguageTag(langCode)!!)
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
            articleToAdd?.title = translatedTitle
        }

        Translator.translate(body).addOnSuccessListener {
                translatedBody ->
            articleToAdd?.body = translatedBody
        }

        return articleToAdd!!
    }
}




