package com.koczuba.kotlin_food_app.retrofit

import com.koczuba.kotlin_food_app.pojo.RandomMealResponse
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<RandomMealResponse>
}