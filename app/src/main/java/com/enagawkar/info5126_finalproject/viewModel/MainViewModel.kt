package com.enagawkar.info5126_finalproject.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.enagawkar.info5126_finalproject.model.ArticleData
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.genai.common.FeatureStatus
import com.google.mlkit.genai.common.GenAiException
import com.google.mlkit.genai.common.DownloadCallback
import com.google.mlkit.genai.summarization.Summarization
import com.google.mlkit.genai.summarization.SummarizationRequest
import com.google.mlkit.genai.summarization.SummarizerOptions
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    //need to make a companion object (singleton) so that the same view model is used on all activities
    companion object ArticleObject {
        var listOfArticles = MutableLiveData<List<ArticleData>>(listOf<ArticleData>())

        fun addArticle(article: ArticleData){
            println(article)
            ArticleObject.listOfArticles.postValue(ArticleObject.listOfArticles.value?.toMutableList()?.apply { add(article) })
        }
    }

    var listOfArticles = ArticleObject.listOfArticles
    var newArt: ArticleData? = null

    public fun translateAndSummarize(title: String, body: String) {
        CoroutineScope(Dispatchers.Default).launch {
            val languageIndetif = LanguageIdentification.getClient()
            languageIndetif.identifyLanguage(title)
                .addOnSuccessListener { languageCode ->
                    if (languageCode == "und") {
                        println("cant")
                    } else {
                        println("Found lang: "+languageCode)
                        CoroutineScope(Dispatchers.Default).launch {
                            newArt = translateTitleAndBody(title, body, languageCode)
                            var s = newArt?.body
                            summarizeBody(s!!)
                            ArticleObject.addArticle(newArt!!)
                        }


                    }
                }
                .addOnFailureListener {
                    println("cant load")
                }
        }
    }

    val summarizerOptions = SummarizerOptions.builder(application)
        .setInputType(SummarizerOptions.InputType.ARTICLE)
        .setOutputType(SummarizerOptions.OutputType.ONE_BULLET)
        .setLanguage(SummarizerOptions.Language.ENGLISH)
        .build()
    val summarizer = Summarization.getClient(summarizerOptions)

    private suspend fun summarizeBody(article: String){

        val featureStatus = summarizer.checkFeatureStatus().get()
        println(featureStatus)
        //Breaks here summarizer.checkFeatureStatus() is returning 0 which means UNAVAILABLE
        if(featureStatus == FeatureStatus.DOWNLOADABLE) {

            summarizer.downloadFeature(object : DownloadCallback {
                override fun onDownloadStarted(bytesToDownload: Long) {
                    println("Download started")
                }

                override fun onDownloadFailed(e: GenAiException) {
                    println("failed u suck")
                }

                override fun onDownloadProgress(totalBytesDownloaded: Long) {
                    println("Download going: "+totalBytesDownloaded)
                }

                override fun onDownloadCompleted() {
                    println("downloaded sum")
                    startSumarize(article)
                }
            })


        }


    }

    private fun startSumarize(article: String){
        val summarizationRequest = SummarizationRequest.builder(article).build()
        var s = summarizer.runInference(summarizationRequest).get().summary
        println(s)
    }


    private suspend fun translateTitleAndBody(title: String, body: String, langCode: String): ArticleData{
        val defer = CoroutineScope(Dispatchers.Default).async {
            var articleToAdd: ArticleData? = ArticleData("", "", "")
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
}






