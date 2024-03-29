package com.example.smerdova_kasatkina

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smerdova_kasatkina.API.Authors
import com.example.smerdova_kasatkina.API.Quotes
import com.example.smerdova_kasatkina.API.RetrofitConnection
import com.example.smerdova_kasatkina.databinding.ActivityListQuotesPageBinding
import com.example.smerdova_kasatkina.databinding.QuotesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListQuotesPage : AppCompatActivity() {
    private lateinit var binding: ActivityListQuotesPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListQuotesPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonHome.setOnClickListener {
            startActivity(Intent(this,HomePage::class.java))
        }
        binding.buttonNext.setOnClickListener {
            finish()
        }
        var IdAuthor=intent.getIntExtra("IdAuthor", 1)
        var FioAuthor=intent.getStringExtra("FioAuthor")
        SetAuthorName(FioAuthor.toString())
        GetAllQuotes(IdAuthor)

    }
    fun GetAllQuotes(IdAuthor:Int)
    {
        CoroutineScope(Dispatchers.IO).launch {
            try {
        RetrofitConnection().GetRetrofit().GetAllQuotes(IdAuthor).enqueue(object:Callback<MutableList<Quotes>>
        {
            override fun onResponse(call: Call<MutableList<Quotes>>, response: Response<MutableList<Quotes>>)
            {
                var r=response
                Log.d("Response", response.code().toString())
                if (response.code()==200)
                {
                    SetResyclerView(response.body()!!)
                }
            }

            override fun onFailure(call: Call<MutableList<Quotes>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
            } catch (e: Exception) {

            }
        }
    }
    fun SetResyclerView(ItemCollection: MutableList<Quotes>)
    {
        binding.recyclerQuoter.layoutManager=LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerQuoter.adapter=RecyclerViewAdapter(ItemCollection)
    }
    class RecyclerViewAdapter(var ItemCollection: MutableList<Quotes>):RecyclerView.Adapter<RecyclerViewViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
            return RecyclerViewViewHolder(QuotesBinding.inflate(LayoutInflater.from(parent.context)))
        }

        override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
            holder.binding.quotesText.text=ItemCollection[position].quote
        }

        override fun getItemCount(): Int {
            return ItemCollection.size
        }
    }
    fun SetAuthorName(Fio:String)
    {
        binding.figureNameList.text=Fio
    }

    class RecyclerViewViewHolder(var binding: QuotesBinding): RecyclerView.ViewHolder(binding.root)
}
