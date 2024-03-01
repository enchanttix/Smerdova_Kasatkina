package com.example.smerdova_kasatkina

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smerdova_kasatkina.databinding.ActivityHomePageBinding

class HomePage : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newTest.setOnClickListener {
            startActivity(Intent(this, TestPageQuotes::class.java))
        }

        binding.randomFigure.setOnClickListener {
            startActivity(Intent(this, FinishTestQuotes::class.java))
        }
    }
}