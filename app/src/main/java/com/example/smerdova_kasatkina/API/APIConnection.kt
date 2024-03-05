package com.example.smerdova_kasatkina.API

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

data class Authors(
    var Id: Int,
    var Fio: String?,
    var YearsOfLife: String?,
    var Description: String?,
    var Image: String?,
    var IdActivity: Int
)
data class Activities(
    var Id: Int,
    var Fio: String?
)
data class Quotes(
    var Id:Int,
    var Quote: String?,
    var IdAuthor:Int
)


interface APIInterface
{
    @GET("api/GetAllActivities")
    fun GetAllActivities(): Call<MutableList<Activities>>
    @GET("api/GetQuote")
    fun GetQuote(@Header("Id")Id: Int): Call<Quotes>
    @GET("api/GetAuthor")
    fun GetAuthor(@Header("Id")Id: Int): Call<Authors>
    @GET("api/GetAllQuotes")
    fun GetAllQuotes(@Header("Id")Id: Int): Call<MutableList<Quotes>>

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