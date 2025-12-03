package com.enagawkar.info5126_finalproject.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enagawkar.info5126_finalproject.model.ArticleData
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel : ViewModel() {

    //need to make a companion object (singleton) so that the same view model is used on all activities
    companion object ArticleObject {
        var listOfArticles = MutableLiveData<List<ArticleData>>(listOf<ArticleData>())

        fun addArticle(article: ArticleData){
            println(article)
            ArticleObject.listOfArticles.postValue(ArticleObject.listOfArticles.value?.toMutableList()?.apply { add(article) })
        }
    }

    var listOfArticles = ArticleObject.listOfArticles
    var buttonToggle = MutableLiveData<Boolean>(true)
    var clearToggle = MutableLiveData<Boolean>(true)
    var newArt: ArticleData? = null

    public fun translateAndSummarize(title: String, body: String, context: Context) {
        CoroutineScope(Dispatchers.Default).launch {
            val languageIndetif = LanguageIdentification.getClient()
            languageIndetif.identifyLanguage(title)
                .addOnSuccessListener { languageCode ->
                    if (languageCode == "und") {
                        println("cant")
                    } else {
                        println("Found lang: "+languageCode)
                        CoroutineScope(Dispatchers.Default).launch {
                            toggleButton()
                            newArt = translateTitleAndBody(title, body, languageCode)
                            ArticleObject.addArticle(newArt!!)
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Your article has been translated",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            toggleButton()
                            clearToggle()
                        }
                    }
                }
                .addOnFailureListener {
                    println("cant load")
                }
        }
    }


    private suspend fun translateTitleAndBody(title: String, body: String, langCode: String): ArticleData{
        val defer = CoroutineScope(Dispatchers.Default).async {
            var articleToAdd: ArticleData? = ArticleData("", "")
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.fromLanguageTag(langCode)!!)
                .setTargetLanguage(TranslateLanguage.ENGLISH)
                .build()
            val Translator = Translation.getClient(options)

            var conditions = DownloadConditions.Builder()
                .requireWifi()
                .build()
            Tasks.await(Translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener {
                    println("downloaded")
                }
                .addOnFailureListener { exception ->
                    println("Can't Download")
                })

            Tasks.await(Translator.translate(title).addOnSuccessListener { translatedTitle ->
                articleToAdd?.title = translatedTitle
            }.addOnFailureListener {
                println("Failed")
            })

            Tasks.await(Translator.translate(body).addOnSuccessListener { translatedBody ->
                articleToAdd?.body = translatedBody
            }.addOnFailureListener {
                println("Failed")
            })


            return@async articleToAdd!!
        }

        return defer.await()
    }

    private fun toggleButton(){
        buttonToggle.postValue(!buttonToggle.value!!)
    }

    private fun clearToggle(){
        clearToggle.postValue(!clearToggle.value!!)
    }
}






