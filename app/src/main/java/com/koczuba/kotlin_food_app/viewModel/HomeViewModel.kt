package com.koczuba.kotlin_food_app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koczuba.kotlin_food_app.pojo.MealDetail
import com.koczuba.kotlin_food_app.pojo.RandomMealResponse
import com.koczuba.kotlin_food_app.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private var randomMealLifeData = MutableLiveData<MealDetail>()


    init {
        getRandomMeal()
    }

    private fun getRandomMeal() {
        RetrofitInstance.mealApi.getRandomMeal().enqueue(object : Callback<RandomMealResponse> {
            override fun onResponse(
                call: Call<RandomMealResponse>,
                response: Response<RandomMealResponse>
            ) {
                if (response.body() == null) {
                    return
                }
                randomMealLifeData.value = response.body()!!.meals[0]

            }

            override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun observeRandomMeal(): LiveData<MealDetail> {
        return randomMealLifeData
    }
}