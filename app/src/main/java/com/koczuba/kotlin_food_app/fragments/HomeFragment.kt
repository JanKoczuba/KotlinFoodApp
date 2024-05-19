package com.koczuba.kotlin_food_app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.koczuba.kotlin_food_app.R
import com.koczuba.kotlin_food_app.databinding.FragmentHomeBinding
import com.koczuba.kotlin_food_app.pojo.MealDetail
import com.koczuba.kotlin_food_app.pojo.RandomMealResponse
import com.koczuba.kotlin_food_app.retrofit.RetrofitInstance
import com.koczuba.kotlin_food_app.viewModel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
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

        observerRandomMeal();
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMeal().observe(viewLifecycleOwner, object : Observer<MealDetail> {
            override fun onChanged(value: MealDetail) {
                Glide.with(this@HomeFragment).load(value.strMealThumb).into(binding.imgRandomMeal)
            }
        })

    }
}