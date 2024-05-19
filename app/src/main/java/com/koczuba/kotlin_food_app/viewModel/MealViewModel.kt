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

class MealViewModel() : ViewModel() {
    private var mealDetailsLiveData = MutableLiveData<MealDetail>()

    fun getMealDetailsById(id: String) {
        RetrofitInstance.mealApi.getMealById(id).enqueue(object : Callback<RandomMealResponse> {
            override fun onResponse(
                call: Call<RandomMealResponse>,
                response: Response<RandomMealResponse>
            ) {
                if (response.body() == null) {
                    return
                }
                mealDetailsLiveData.value = response.body()!!.meals[0]
            }

            override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
                Log.e("MealActivity", t.message.toString())
            }

        })
    }
    fun observeMealDetail(): LiveData<MealDetail> {
        return mealDetailsLiveData
    }
}