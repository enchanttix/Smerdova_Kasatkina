package com.example.smerdova_kasatkina

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.smerdova_kasatkina.API.Authors
import com.example.smerdova_kasatkina.API.RetrofitConnection
import com.example.smerdova_kasatkina.databinding.ActivityRandomFigureInformationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RandomFigureInformation : AppCompatActivity() {
    private lateinit var binding: ActivityRandomFigureInformationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomFigureInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonHome.setOnClickListener {
            startActivity(Intent(this,HomePage::class.java))
        }

        RetrofitConnection().GetRetrofit().GetAuthor(5).enqueue(object:Callback<Authors>{
            override fun onResponse(call: Call<Authors>, response: Response<Authors>) {
                if (response.code()==200)
                {
                    Log.println(Log.INFO, "API", "Я зашел сюда")
                    SetInformationAuthors(response.body()!!)
                }
            }

            override fun onFailure(call: Call<Authors>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    fun SetInformationAuthors(Author: Authors)
    {
        Log.println(Log.INFO, "API", "Я зашел в биндинг")
        binding.figureName.text=Author.Fio
        binding.figureInformation.text=Author.Description
        binding.figureDate.text=Author.YearsOfLife
    }
}