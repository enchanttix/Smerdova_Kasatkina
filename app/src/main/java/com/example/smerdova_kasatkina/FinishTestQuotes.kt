package com.example.smerdova_kasatkina

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smerdova_kasatkina.databinding.ActivityFinishTestQuotesBinding

class FinishTestQuotes : AppCompatActivity() {
    private lateinit var binding: ActivityFinishTestQuotesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishTestQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}