package com.example.smerdova_kasatkina

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smerdova_kasatkina.databinding.ActivityRandomFigureInformationBinding

class RandomFigureInformation : AppCompatActivity() {
    private lateinit var binding: ActivityRandomFigureInformationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomFigureInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonHome.setOnClickListener {
            startActivity(Intent(this,HomePage::class.java))
        }
    }
}