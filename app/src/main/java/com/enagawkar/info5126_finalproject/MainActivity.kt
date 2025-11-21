package com.enagawkar.info5126_finalproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.enagawkar.info5126_finalproject.databinding.ActivityMainBinding
import com.google.mlkit.nl.languageid.LanguageIdentification

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val languageIndetif = LanguageIdentification.getClient()
        languageIndetif.identifyLanguage("It has been a great year so far for gamers, as 2025 has delivered several outstanding games already. The year isn't over yet, but so far we've had sleeper hits like Blue Prince and Clair Obscur: Expedition 33 alongside fun blockbuster titles like Assassin's Creed Shadows and Doom: The Dark Ages")
            .addOnSuccessListener { languageCode ->
                if(languageCode == "und"){
                    println("cant")
                }else {
                    println(languageCode)
                }
            }
            .addOnFailureListener {
                println("cant load")
            }
    }

    fun onClickDescription(view: View){
        val intent = Intent(this, DescriptionActivity::class.java).apply { }
        startActivity(intent)
    }


}