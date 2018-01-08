package com.siddapps.android.refrigeratorsurprise.network

import com.siddapps.android.refrigeratorsurprise.data.RecipeDetailsResponse
import com.siddapps.android.refrigeratorsurprise.data.RecipeResponse
import com.siddapps.android.refrigeratorsurprise.utils.Const.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface APIInterface {

    @GET("search?key=" + API_KEY)
    fun searchRecipesByIngredients(@Query("q") ingredients: String): Observable<RecipeResponse>

    @GET("get?key=" + API_KEY)
    fun getRecipeById(@Query("rId") id: String): Observable<RecipeDetailsResponse>

}