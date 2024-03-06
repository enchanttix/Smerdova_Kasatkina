package com.example.smerdova_kasatkina

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.smerdova_kasatkina.API.Authors
import com.example.smerdova_kasatkina.API.Quotes
import com.example.smerdova_kasatkina.API.RetrofitConnection
import com.example.smerdova_kasatkina.databinding.ActivityTestPageQuotesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class TestPageQuotes : AppCompatActivity() {
    private lateinit var binding: ActivityTestPageQuotesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestPageQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var randomQuote = Random.nextInt(1, 500)//выбор рандомной цитаты

        var Quote:String=""
        var IdAuthor:Int=0

        RetrofitConnection().GetRetrofit().GetQuote(randomQuote).enqueue(object:Callback<Quotes>//подключение
        {
            override fun onResponse(call: Call<Quotes>, response: Response<Quotes>)
            {
                Log.d("Author", response.code().toString())
                var r=response
                if(response.code()==200)
                {
                    Log.d("Author", "я зашел в цитаты")
                    Quote= response.body()!!.quote.toString()
                    IdAuthor=response.body()!!.id_author
                }
            }

            override fun onFailure(call: Call<Quotes>, t: Throwable)
            {
                TODO("Not yet implemented")
            }
        })

        binding.quotesText.text=Quote//вставка цитаты
        var FioCorrectAuthor=GetAuthors(IdAuthor)//правильный автор

        var IdAuthorsUncorrect1=GetRandomAuthors(IdAuthor)//код первого неправильного автора
        var IdAuthorsUncorrect2=GetRandomAuthors(IdAuthor)//код второго неправильного автора
        var IdAuthorsUncorrect3=GetRandomAuthors(IdAuthor)//код третьего неправильного автора

        var FioUncorrectAuthor1=GetAuthors(IdAuthorsUncorrect1)//первый правильный автор фио
        var FioUncorrectAuthor2=GetAuthors(IdAuthorsUncorrect2)//второй правильный автор фио
        var FioUncorrectAuthor3=GetAuthors(IdAuthorsUncorrect3)//третий правильный автор фио

        var IdCorrectButton:Int=RandomButtonAnswer(FioCorrectAuthor, FioUncorrectAuthor1, FioUncorrectAuthor2, FioUncorrectAuthor3)//зарандомить ответы и получить код правильной кнопки
        binding.figureAnswers1.setOnClickListener { binding.figureAnswers1.setBackgroundResource(R.drawable.background_button_green) }
        binding.figureAnswers2.setOnClickListener { binding.figureAnswers2.setBackgroundResource(R.drawable.background_button_green) }
        binding.figureAnswers3.setOnClickListener { binding.figureAnswers3.setBackgroundResource(R.drawable.background_button_green) }
        binding.figureAnswers4.setOnClickListener { binding.figureAnswers4.setBackgroundResource(R.drawable.background_button_green) }
        binding.buttonNext.setOnClickListener()
        {
            if(IdCorrectButton==1)
            {
                binding.figureAnswers1.setBackgroundResource(R.drawable.background_button_correct)
                binding.figureAnswers2.setBackgroundResource(R.drawable.background_button_uncorrect)
                binding.figureAnswers3.setBackgroundResource(R.drawable.background_button_uncorrect)
                binding.figureAnswers4.setBackgroundResource(R.drawable.background_button_uncorrect)
            }
            else if(IdCorrectButton==2)
            {
                binding.figureAnswers1.setBackgroundResource(R.drawable.background_button_uncorrect)
                binding.figureAnswers2.setBackgroundResource(R.drawable.background_button_correct)
                binding.figureAnswers3.setBackgroundResource(R.drawable.background_button_uncorrect)
                binding.figureAnswers4.setBackgroundResource(R.drawable.background_button_uncorrect)
            }
            else if(IdCorrectButton==3)
            {
                binding.figureAnswers1.setBackgroundResource(R.drawable.background_button_uncorrect)
                binding.figureAnswers2.setBackgroundResource(R.drawable.background_button_uncorrect)
                binding.figureAnswers3.setBackgroundResource(R.drawable.background_button_correct)
                binding.figureAnswers4.setBackgroundResource(R.drawable.background_button_uncorrect)
            }
            else if(IdCorrectButton==4)
            {
                binding.figureAnswers1.setBackgroundResource(R.drawable.background_button_uncorrect)
                binding.figureAnswers2.setBackgroundResource(R.drawable.background_button_uncorrect)
                binding.figureAnswers3.setBackgroundResource(R.drawable.background_button_uncorrect)
                binding.figureAnswers4.setBackgroundResource(R.drawable.background_button_correct)
            }
            val intent= Intent(this, TestPageQuotes::class.java)
            android.os.Handler().postDelayed({
                startActivity(intent)
            }, 3000)
        }
    }
    fun GetRandomAuthors(CorrectId:Int):Int//получить код рандомного автора
    {
        var id:Int=0
        while (id==CorrectId)
        {
            id=Random.nextInt(1, 100)
        }
        return id
    }
    fun GetAuthors(Id:Int):String//получить автора из бд
    {
        var Author:String=""

        RetrofitConnection().GetRetrofit().GetAuthor(Id).enqueue(object:Callback<Authors>{
            override fun onResponse(call: Call<Authors>, response: Response<Authors>) {
                var r=response
                if(response.code()==200)
                {
                    Author=response.body()!!.fio.toString()
                }
            }

            override fun onFailure(call: Call<Authors>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        return Author
    }
    fun RandomButtonAnswer(CorrectAuthor:String, Author1:String, Author2:String, Author3:String):Int//расставить авторов по кнопкам
    {
        var RandomCorrectButton=Random.nextInt(1, 4)//выбор рандомной цитаты
        if(RandomCorrectButton==1)
        {

            binding.figureAnswers1.text=CorrectAuthor
            binding.figureAnswers2.text=Author1
            binding.figureAnswers3.text=Author2
            binding.figureAnswers4.text=Author3
        }
        else if(RandomCorrectButton==2)
        {
            binding.figureAnswers1.text=Author1
            binding.figureAnswers2.text=CorrectAuthor
            binding.figureAnswers3.text=Author2
            binding.figureAnswers4.text=Author3
        }
        else if(RandomCorrectButton==3)
        {
            binding.figureAnswers1.text=Author1
            binding.figureAnswers2.text=Author2
            binding.figureAnswers3.text=CorrectAuthor
            binding.figureAnswers4.text=Author3
        }
        else
        {
            binding.figureAnswers1.text=Author1
            binding.figureAnswers2.text=Author2
            binding.figureAnswers3.text=Author3
            binding.figureAnswers4.text=CorrectAuthor
        }
        return RandomCorrectButton
    }
}
