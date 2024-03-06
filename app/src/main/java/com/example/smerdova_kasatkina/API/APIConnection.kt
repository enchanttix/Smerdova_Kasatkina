package com.example.smerdova_kasatkina.API

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

data class Authors(
    var id: Int,
    var fio: String?,
    var years_of_life: String?,
    var description: String?,
    var image: String?,
    var id_activity: Int
)
data class Activities(
    var id: Int,
    var activity: String?
)
data class Quotes(
    var id:Int,
    var quote: String?,
    var id_author:Int
)


interface APIInterface
{
    @GET("api/GetAllActivities")
    fun GetAllActivities(): Call<MutableList<Activities>>
    @GET("api/GetQuote")
    fun GetQuote(@Query("Id")Id: Int): Call<Quotes>
    @GET("api/GetAuthor")
    fun GetAuthor(@Query("Id")Id: Int): Call<Authors>
    @GET("api/GetAllQuotes")
    fun GetAllQuotes(@Query("Id")Id: Int): Call<MutableList<Quotes>>

}
public class RetrofitConnection
{
    fun GetRetrofit():APIInterface
    {
        return Retrofit.Builder()
            .baseUrl("https://iis.ngknn.ru/NGKNN/%D0%9C%D0%B0%D0%BC%D1%88%D0%B5%D0%B2%D0%B0%D0%AE%D0%A1/2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIInterface::class.java)
    }
}