package com.example.smerdova_kasatkina

import android.content.ContentValues
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import com.example.smerdova_kasatkina.API.APIInterface
import com.example.smerdova_kasatkina.API.Authors
import com.example.smerdova_kasatkina.API.Quotes
import com.example.smerdova_kasatkina.API.RetrofitConnection
import com.example.smerdova_kasatkina.databinding.ActivityTestPageQuotesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class TestPageQuotes : AppCompatActivity() {
    private lateinit var binding: ActivityTestPageQuotesBinding
    var FioCorrectAuthor:String?=""
    var Quote:String?=""
    var IdAuthorCorrect:Int?=0
    var IdAuthorsUncorrect1:Int?=0//код первого неправильного автора
    var IdAuthorsUncorrect2:Int?=0//код второго неправильного автора
    var IdAuthorsUncorrect3:Int?=0//код третьего неправильного автора
    var RandomCorrectButton:Int?=0
    var IdCorrectButton:Int?=0
    var FioUncorrectAuthor1:String?=""
    var FioUncorrectAuthor2:String?=""
    var FioUncorrectAuthor3:String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestPageQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var randomQuote = Random.nextInt(1, 500)//выбор рандомной цитаты
        //GetIdAuthor(randomQuote)
        var Quote: String = ""
        var IdNew: Int = 0
        var i:Int=0
        binding.buttonHome.setOnClickListener {
            startActivity(Intent(this,HomePage::class.java))
        }
        RetrofitConnection().GetRetrofit().GetQuote(randomQuote).enqueue(object : Callback<Quotes> {
            override fun onResponse(call: Call<Quotes>, response: Response<Quotes>) {
                Log.d("Quote", response.code().toString())
                var r = response
                if (response.isSuccessful) {
                    Log.d("Quote", "я зашел в цитаты")
                    Quote = response.body()!!.quote.toString()
                    Log.d("Quote", Quote)
                    IdAuthorCorrect = response.body()!!.id_author
                    Log.d("QuoteIdAuthor", IdAuthorCorrect.toString())
                    //SetQuote(Quote)
                    binding.quotesText.text = Quote//вставка цитаты

                    RetrofitConnection().GetRetrofit().GetAuthor(IdAuthorCorrect!!)
                        .enqueue(object : Callback<Authors> {
                            override fun onResponse(call: Call<Authors>, response: Response<Authors>) {
                                Log.d("Authors", response.code().toString())
                                var r = response
                                if (response.code() == 200) {

                                    FioCorrectAuthor = response.body()!!.fio.toString()
                                    Log.d("AuthorCorrect", FioCorrectAuthor!!)
                                    while (i!=IdAuthorCorrect)
                                    {
                                        IdNew=Random.nextInt(1, 100)
                                        while (IdNew==IdAuthorCorrect){
                                            IdNew=Random.nextInt(1, 100)
                                        }
                                        i=IdAuthorCorrect!!
                                    }

                                    RetrofitConnection().GetRetrofit().GetAuthor(IdNew)
                                        .enqueue(object : Callback<Authors> {
                                            override fun onResponse(
                                                call: Call<Authors>,
                                                response: Response<Authors>
                                            ) {
                                                Log.d("Authors", response.code().toString())
                                                var r = response
                                                if (response.code() == 200) {
                                                    //IdNew = GetRandomAuthors(Id)
                                                    FioUncorrectAuthor1 =
                                                        response.body()!!.fio.toString()
                                                    Log.d("Author1", FioUncorrectAuthor1!!)
                                                    i=0
                                                    while (i!=IdAuthorCorrect)
                                                    {
                                                        IdNew=Random.nextInt(1, 100)
                                                        while (IdNew==IdAuthorCorrect){
                                                            IdNew=Random.nextInt(1, 100)
                                                        }
                                                        i=IdAuthorCorrect!!
                                                    }

                                                    RetrofitConnection().GetRetrofit()
                                                        .GetAuthor(IdNew)
                                                        .enqueue(object :
                                                            Callback<Authors> {
                                                            override fun onResponse(
                                                                call: Call<Authors>,
                                                                response: Response<Authors>
                                                            ) {
                                                                Log.d(
                                                                    "Authors",
                                                                    response.code()
                                                                        .toString()
                                                                )
                                                                var r = response
                                                                if (response.code() == 200) {

                                                                    FioUncorrectAuthor2 =
                                                                        response.body()!!.fio.toString()
                                                                    Log.d(
                                                                        "Author2",
                                                                        FioUncorrectAuthor2!!
                                                                    )
                                                                    i=0
                                                                    while (i!=IdAuthorCorrect)
                                                                    {
                                                                        IdNew=Random.nextInt(1, 100)
                                                                        while (IdNew==IdAuthorCorrect){
                                                                            IdNew=Random.nextInt(1, 100)
                                                                        }
                                                                        i=IdAuthorCorrect!!
                                                                    }

                                                                    RetrofitConnection().GetRetrofit()
                                                                        .GetAuthor(
                                                                            IdNew
                                                                        )
                                                                        .enqueue(
                                                                            object :
                                                                                Callback<Authors> {
                                                                                override fun onResponse(
                                                                                    call: Call<Authors>,
                                                                                    response: Response<Authors>
                                                                                ) {
                                                                                    Log.d(
                                                                                        "Authors",
                                                                                        response.code()
                                                                                            .toString()
                                                                                    )
                                                                                    var r =
                                                                                        response
                                                                                    if (response.code() == 200) {

                                                                                        FioUncorrectAuthor3 =
                                                                                            response.body()!!.fio.toString()
                                                                                        Log.d(
                                                                                            "Author3",
                                                                                            FioUncorrectAuthor3!!
                                                                                        )
                                                                                        RandomCorrectButton = Random.nextInt(1, 4)//выбор рандомной цитаты
                                                                                        if (RandomCorrectButton == 1)
                                                                                        {
                                                                                            IdCorrectButton=RandomCorrectButton
                                                                                            binding.figureAnswers1.text = FioCorrectAuthor.toString()
                                                                                            binding.figureAnswers2.text = FioUncorrectAuthor1.toString()
                                                                                            binding.figureAnswers3.text = FioUncorrectAuthor2.toString()
                                                                                            binding.figureAnswers4.text = FioUncorrectAuthor3.toString()
                                                                                        } else if (RandomCorrectButton == 2)
                                                                                        {
                                                                                            IdCorrectButton=RandomCorrectButton
                                                                                            binding.figureAnswers1.text = FioUncorrectAuthor1.toString()
                                                                                            binding.figureAnswers2.text = FioCorrectAuthor.toString()
                                                                                            binding.figureAnswers3.text = FioUncorrectAuthor2.toString()
                                                                                            binding.figureAnswers4.text = FioUncorrectAuthor3.toString()
                                                                                        } else if (RandomCorrectButton == 3)
                                                                                        {
                                                                                            IdCorrectButton=RandomCorrectButton
                                                                                            binding.figureAnswers1.text = FioUncorrectAuthor1.toString()
                                                                                            binding.figureAnswers2.text = FioUncorrectAuthor2.toString()
                                                                                            binding.figureAnswers3.text = FioCorrectAuthor.toString()
                                                                                            binding.figureAnswers4.text = FioUncorrectAuthor3.toString()
                                                                                        } else
                                                                                        {
                                                                                            IdCorrectButton=RandomCorrectButton
                                                                                            binding.figureAnswers1.text = FioUncorrectAuthor1.toString()
                                                                                            binding.figureAnswers2.text = FioUncorrectAuthor2.toString()
                                                                                            binding.figureAnswers3.text = FioUncorrectAuthor3.toString()
                                                                                            binding.figureAnswers4.text = FioCorrectAuthor.toString()
                                                                                        }
                                                                                    }
                                                                                }
                                                                                override fun onFailure(
                                                                                    call: Call<Authors>,
                                                                                    t: Throwable
                                                                                ) {
                                                                                    TODO(
                                                                                        "Not yet implemented"
                                                                                    )
                                                                                }
                                                                            })
                                                                }
                                                            }

                                                            override fun onFailure(
                                                                call: Call<Authors>,
                                                                t: Throwable
                                                            ) {
                                                                TODO("Not yet implemented")
                                                            }
                                                        })
                                                }
                                            }

                                            override fun onFailure(
                                                call: Call<Authors>,
                                                t: Throwable
                                            ) {
                                                TODO("Not yet implemented")
                                            }
                                        })
                                }
                            }

                            override fun onFailure(call: Call<Authors>, t: Throwable) {
                                TODO("Not yet implemented")
                            }
                        })
                }
            }
            override fun onFailure(call: Call<Quotes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })


        Log.d("varAuthorCorrect", FioCorrectAuthor!!)
        Log.d("varAuthorUncorrect1", FioUncorrectAuthor1!!)
        Log.d("varAuthorUncorrect2", FioUncorrectAuthor2!!)
        Log.d("varAuthorUncorrect3", FioUncorrectAuthor3!!)

        binding.figureAnswers1.setOnClickListener()
        {
            binding.figureAnswers1.setBackgroundResource(R.drawable.background_button_green)
            binding.figureAnswers2.setBackgroundResource(R.drawable.background_button)
            binding.figureAnswers3.setBackgroundResource(R.drawable.background_button)
            binding.figureAnswers4.setBackgroundResource(R.drawable.background_button)
        }
        binding.figureAnswers2.setOnClickListener()
        {
            binding.figureAnswers2.setBackgroundResource(R.drawable.background_button_green)
            binding.figureAnswers1.setBackgroundResource(R.drawable.background_button)
            binding.figureAnswers3.setBackgroundResource(R.drawable.background_button)
            binding.figureAnswers4.setBackgroundResource(R.drawable.background_button)
        }
        binding.figureAnswers3.setOnClickListener()
        {
            binding.figureAnswers3.setBackgroundResource(R.drawable.background_button_green)
            binding.figureAnswers1.setBackgroundResource(R.drawable.background_button)
            binding.figureAnswers2.setBackgroundResource(R.drawable.background_button)
            binding.figureAnswers4.setBackgroundResource(R.drawable.background_button)
        }
        binding.figureAnswers4.setOnClickListener()
        {
            binding.figureAnswers4.setBackgroundResource(R.drawable.background_button_green)
            binding.figureAnswers1.setBackgroundResource(R.drawable.background_button)
            binding.figureAnswers2.setBackgroundResource(R.drawable.background_button)
            binding.figureAnswers3.setBackgroundResource(R.drawable.background_button)
        }
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





}
