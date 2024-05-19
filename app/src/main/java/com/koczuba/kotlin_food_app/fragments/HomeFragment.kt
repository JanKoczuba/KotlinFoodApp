package com.koczuba.kotlin_food_app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.koczuba.kotlin_food_app.R
import com.koczuba.kotlin_food_app.databinding.FragmentHomeBinding
import com.koczuba.kotlin_food_app.pojo.MealDetail
import com.koczuba.kotlin_food_app.pojo.RandomMealResponse
import com.koczuba.kotlin_food_app.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RetrofitInstance.mealApi.getRandomMeal().enqueue(object : Callback<RandomMealResponse> {
            override fun onResponse(
                call: Call<RandomMealResponse>,
                response: Response<RandomMealResponse>
            ) {
                if (response.body() == null) {
                    return
                }
                val randomMeal: MealDetail = response.body()!!.meals[0]
                Glide.with(this@HomeFragment).load(randomMeal.strMealThumb)
                    .into(binding.imgRandomMeal)
            }

            override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

}