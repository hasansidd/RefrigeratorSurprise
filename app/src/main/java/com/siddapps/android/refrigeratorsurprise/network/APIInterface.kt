package com.siddapps.android.refrigeratorsurprise.network

import com.siddapps.android.refrigeratorsurprise.data.RecipeDetailsResponse
import com.siddapps.android.refrigeratorsurprise.data.RecipeResponse
import com.siddapps.android.refrigeratorsurprise.utils.Const.Companion.API_KEY
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface APIInterface {

    @GET("search?key=" + API_KEY)
    fun searchRecipesByIngredients(@Query("q") ingredients: String): Observable<RecipeResponse>

    @GET("get?key=" + API_KEY)
    fun getRecipeById(@Query("rId") id: String): Deferred<RecipeDetailsResponse>
}