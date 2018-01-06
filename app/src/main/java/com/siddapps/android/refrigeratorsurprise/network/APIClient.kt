package com.siddapps.android.refrigeratorsurprise.network

import android.util.Log
import com.google.gson.GsonBuilder
import com.siddapps.android.refrigeratorsurprise.data.RecipeDetailsListing
import com.siddapps.android.refrigeratorsurprise.data.RecipeListing
import com.siddapps.android.refrigeratorsurprise.utils.Const.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    val apiService: APIService
    val TAG = "APIClient"

    init {

        val gson = GsonBuilder().setLenient().create()
        var retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        apiService = retrofit.create(APIService::class.java)
    }

    fun searchByIngredients(ingredients: List<String>): Call<RecipeListing> {
        return apiService.searchRecipesByIngredients(ingredients.joinToString(","))
    }

    fun getRecipeById(recipeID: String): Call<RecipeDetailsListing> {
        return apiService.getRecipeById(recipeID)
    }

}