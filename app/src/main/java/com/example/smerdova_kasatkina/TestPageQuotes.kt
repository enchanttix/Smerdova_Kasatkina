package com.example.smerdova_kasatkina

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smerdova_kasatkina.databinding.ActivityTestPageQuotesBinding

class TestPageQuotes : AppCompatActivity() {
    private lateinit var binding: ActivityTestPageQuotesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestPageQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}