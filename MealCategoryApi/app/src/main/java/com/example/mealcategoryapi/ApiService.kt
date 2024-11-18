package com.example.mealcategoryapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

val retrofit = Retrofit.Builder().baseUrl("https://www.themealdb.com/api/json/v1/1/")
    .addConverterFactory(GsonConverterFactory.create()).build()
val retrofitService = retrofit.create(ApiService::class.java)
interface ApiService {
    @GET("search.php")
    suspend fun getMealByFirstLetter(@Query("f") letter:String): MealResponse
}