package com.example.smerdova_kasatkina

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.smerdova_kasatkina.API.APIInterface
import com.example.smerdova_kasatkina.API.Authors
import com.example.smerdova_kasatkina.API.Quotes
import com.example.smerdova_kasatkina.API.RetrofitConnection
import com.example.smerdova_kasatkina.databinding.ActivityRandomFigureInformationBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class RandomFigureInformation : AppCompatActivity() {
    private lateinit var binding: ActivityRandomFigureInformationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomFigureInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonHome.setOnClickListener {
            startActivity(Intent(this, HomePage::class.java))
        }
        intent=Intent(this, ListQuotesPage::class.java)
        binding.buttonQuotes.setOnClickListener {
            startActivity(intent)
        }
        binding.buttonNext.setOnClickListener()
        {
            startActivity(Intent(this, RandomFigureInformation::class.java))
        }
        var IdAuthor:Int=0
        var randomAuthor = Random.nextInt(1, 100)

        CoroutineScope(Dispatchers.IO).launch {//получение имени автора
            try {
                RetrofitConnection().GetRetrofit().GetAuthor(randomAuthor).enqueue(object : Callback<Authors> {
                    override fun onResponse(call: Call<Authors>, response: Response<Authors>) {
                        Log.d("Response", response.code().toString())
                        if (response.code() == 200) {
                            Log.d("Response", response.code().toString())
                            IdAuthor=response.body()!!.id
                            intent.putExtra("IdAuthor", IdAuthor)
                            intent.putExtra("FioAuthor", response.body()!!.fio.toString())
                            SetInformationAuthors(response.body()!!)
                        }
                    }

                    private fun SetInformationAuthors(Author: Authors) {
                        Log.println(Log.INFO, "API", "Я зашел в биндинг")
                        binding.figureName.text = Author.fio
                        binding.figureInformation.text = Author.description
                        binding.figureDate.text = Author.years_of_life
                        Picasso.get().load(Author.image).into(binding.imageFigure)
                    }

                    override fun onFailure(call: Call<Authors>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, e.toString()) // Вывод информации на консоль
            }

        }

    }
}

