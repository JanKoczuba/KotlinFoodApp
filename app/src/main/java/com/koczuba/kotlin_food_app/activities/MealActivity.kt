package com.koczuba.kotlin_food_app.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.koczuba.kotlin_food_app.R
import com.koczuba.kotlin_food_app.databinding.ActivityMealBinding
import com.koczuba.kotlin_food_app.fragments.HomeFragment
import com.koczuba.kotlin_food_app.pojo.MealDetail
import com.koczuba.kotlin_food_app.viewModel.HomeViewModel
import com.koczuba.kotlin_food_app.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youtubeLink: String
    private lateinit var mealyMvvm: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mealyMvvm = ViewModelProvider(this)[MealViewModel::class.java]

        loadingData()
        getMealInfoFromIntent()
        setInfoInViews()

        mealyMvvm.getMealDetailsById(mealId)
        observerMealDetailLiveData()
        loadedData()

        onYouTubeImgClick()
    }

    private fun onYouTubeImgClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))

        }
    }

    private fun observerMealDetailLiveData() {
        mealyMvvm.observeMealDetail().observe(this
        ) { value ->
            val categoryText = "${getString(R.string.category)}: ${value.strCategory}"
            val areaInfoText = "${getString(R.string.area)}: ${value.strArea}"


            binding.tvCategoryInfo.text = categoryText
            binding.tvAreaInfo.text = areaInfoText
            binding.tvInstructions.text = value.strInstructions

            youtubeLink = value.strYoutube
        }
    }

    private fun setInfoInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))


    }

    private fun getMealInfoFromIntent() {
        val intent = intent

        this.mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        this.mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        this.mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }

    private fun loadingData() {
        binding.progressBar.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.favoritiesSaveButton.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
        binding.tvAreaInfo.visibility = View.INVISIBLE

    }

    private fun loadedData() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.favoritiesSaveButton.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
        binding.tvAreaInfo.visibility = View.VISIBLE

    }

}