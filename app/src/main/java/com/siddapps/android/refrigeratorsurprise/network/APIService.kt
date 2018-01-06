package com.siddapps.android.refrigeratorsurprise.network

import com.siddapps.android.refrigeratorsurprise.data.RecipeDetailsListing
import com.siddapps.android.refrigeratorsurprise.data.RecipeListing
import com.siddapps.android.refrigeratorsurprise.utils.Const.Companion.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("search?key=" + API_KEY)
    fun searchRecipesByIngredients(@Query("q") ingredients: String): Call<RecipeListing>

    @GET("get?key=" + API_KEY)
    fun getRecipeById(@Query("rId") id: String): Call<RecipeDetailsListing>

}