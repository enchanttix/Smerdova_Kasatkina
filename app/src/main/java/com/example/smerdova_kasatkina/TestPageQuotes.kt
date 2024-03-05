package com.example.smerdova_kasatkina

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smerdova_kasatkina.API.Authors
import com.example.smerdova_kasatkina.API.Quotes
import com.example.smerdova_kasatkina.API.RetrofitConnection
import com.example.smerdova_kasatkina.databinding.ActivityTestPageQuotesBinding
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
        lateinit var _Quote:Quotes
        _Quote.Id=0
        _Quote.Quote=""
        _Quote.IdAuthor=0



        RetrofitConnection().GetRetrofit().GetQuote(randomQuote).enqueue(object:Callback<Quotes>{
            override fun onResponse(call: Call<Quotes>, response: Response<Quotes>) {
                if(response.code()==200)
                {
                    _Quote=response.body()!!
                }
            }

            override fun onFailure(call: Call<Quotes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        binding.quotesText.text=_Quote.Quote//вставка цитаты
        var FioCorrectAuthor=GetAuthors(_Quote.IdAuthor)//правильный автор

        var IdAuthorsUncorrect1=GetRandomAuthors(_Quote.IdAuthor)//первый неправильный автор
        var IdAuthorsUncorrect2=GetRandomAuthors(_Quote.IdAuthor)//второй неправильный автор
        var IdAuthorsUncorrect3=GetRandomAuthors(_Quote.IdAuthor)//третий неправильный автор

        var FioUncorrectAuthor1=GetAuthors(IdAuthorsUncorrect1)//первый правильный автор
        var FioUncorrectAuthor2=GetAuthors(IdAuthorsUncorrect2)//второй правильный автор
        var FioUncorrectAuthor3=GetAuthors(IdAuthorsUncorrect3)//третий правильный автор

        var IdCorrectButton:Int=RandomButtonAnswer(FioCorrectAuthor, FioUncorrectAuthor1, FioUncorrectAuthor2, FioUncorrectAuthor3)//зарандомить ответы и получить код правильной кнопки
        binding.figureAnswers1.setOnClickListener { binding.figureAnswers1.setBackgroundResource(R.drawable.background_button_green) }
        binding.figureAnswers2.setOnClickListener { binding.figureAnswers1.setBackgroundResource(R.drawable.background_button_green) }
        binding.figureAnswers3.setOnClickListener { binding.figureAnswers1.setBackgroundResource(R.drawable.background_button_green) }
        binding.figureAnswers4.setOnClickListener { binding.figureAnswers1.setBackgroundResource(R.drawable.background_button_green) }
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
        lateinit var a:Authors
        a.Id=0
        RetrofitConnection().GetRetrofit().GetAuthor(Id).enqueue(object:Callback<Authors>{
            override fun onResponse(call: Call<Authors>, response: Response<Authors>) {
                if(response.code()==200)
                {
                    a=response.body()!!
                }
            }

            override fun onFailure(call: Call<Authors>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        return a.Fio!!
    }
    fun RandomButtonAnswer(CorrectAuthor:String, Author1:String, Author2:String, Author3:String):Int//расставить авторов по кнопкам
    {
        var RandomCorrectButton=Random.nextInt(1, 4)//выбор рандомной цитаты
        if(RandomCorrectButton==1)
        {

            binding.figureAnswers1.text=CorrectAuthor
            binding.figureAnswers2.text=Author1
            binding.figureAnswers1.text=Author2
            binding.figureAnswers1.text=Author3
        }
        else if(RandomCorrectButton==2)
        {
            binding.figureAnswers1.text=Author1
            binding.figureAnswers2.text=CorrectAuthor
            binding.figureAnswers1.text=Author2
            binding.figureAnswers1.text=Author3
        }
        else if(RandomCorrectButton==3)
        {
            binding.figureAnswers1.text=Author1
            binding.figureAnswers2.text=Author2
            binding.figureAnswers1.text=CorrectAuthor
            binding.figureAnswers1.text=Author3
        }
        else
        {
            binding.figureAnswers1.text=Author1
            binding.figureAnswers2.text=Author2
            binding.figureAnswers1.text=Author3
            binding.figureAnswers1.text=CorrectAuthor
        }
        return RandomCorrectButton
    }
}