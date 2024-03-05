package com.example.smerdova_kasatkina

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.smerdova_kasatkina.API.APIInterface
import com.example.smerdova_kasatkina.API.Authors
import com.example.smerdova_kasatkina.databinding.ActivityRandomFigureInformationBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class RandomFigureInformation : AppCompatActivity() {
    private lateinit var binding: ActivityRandomFigureInformationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomFigureInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonHome.setOnClickListener {
            startActivity(Intent(this, HomePage::class.java))
        }
        binding.buttonQuotes.setOnClickListener {
            startActivity((Intent(this, ListQuotesPage::class.java)))
        }

              /*RetrofitConnection().GetRetrofit().GetAuthor(5).enqueue(object: Callback<Authors>{
                  override fun onResponse(call: Call<Authors>, response: Response<Authors>) {
                      Log.d("Response", response.code().toString())
                      if (response.code()==204)
                      {
                          Log.d("Response", response.code().toString())
                          SetInformationAuthors(response.body()!!)
                      }
                  }

                  override fun onFailure(call: Call<Authors>, t: Throwable) {
                      TODO("Not yet implemented")
                  }
              })*/

        getdata()

    }

    fun getdata() {
        var retrofit = Retrofit.Builder()
            .baseUrl("https://iis.ngknn.ru/NGKNN/МамшеваЮС/2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var response = retrofit.GetAuthor(5).awaitResponse()
                Log.d("Response", response.code().toString())
                if (response.isSuccessful) {
                      val data = response.body()!! // Получаем тело ответа
                      /*Log.d("Response", data.Fio.toString())
                    binding.figureName.text = response.body()!!.Fio
                    binding.figureInformation.text = response.body()!!.Description
                    binding.figureDate.text = response.body()!!.YearsOfLife*/
                    Log.d("Response", response.code().toString()) // Вывод информации на консоль
                }

            } catch (e: Exception) {
                Log.d(ContentValues.TAG, e.toString()) // Вывод информации на консоль
            }
        }
    }

    fun SetInformationAuthors(Author: Authors) {
        Log.println(Log.INFO, "API", "Я зашел в биндинг")
        binding.figureName.text = Author.Fio
        binding.figureInformation.text = Author.Description
        binding.figureDate.text = Author.YearsOfLife
    }
}