package com.example.smerdova_kasatkina

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smerdova_kasatkina.API.Quotes
import com.example.smerdova_kasatkina.API.RetrofitConnection
import com.example.smerdova_kasatkina.databinding.ActivityListQuotesPageBinding
import com.example.smerdova_kasatkina.databinding.QuotesBinding
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
            startActivity(Intent(this, ListQuotesPage::class.java))
        }
        RetrofitConnection().GetRetrofit().GetAllQuotes(5).enqueue(object:Callback<MutableList<Quotes>>{
            override fun onResponse(
                call: Call<MutableList<Quotes>>,
                response: Response<MutableList<Quotes>>
            ) {
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
            holder.binding.quotesText.text=ItemCollection[position].Quote
        }

        override fun getItemCount(): Int {
            return ItemCollection.size
        }
    }

    class RecyclerViewViewHolder(var binding: QuotesBinding): RecyclerView.ViewHolder(binding.root)
}
