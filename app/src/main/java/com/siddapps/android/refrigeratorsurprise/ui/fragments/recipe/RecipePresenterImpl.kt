package com.siddapps.android.refrigeratorsurprise.ui.fragments.recipe

import com.siddapps.android.refrigeratorsurprise.data.Recipe
import com.siddapps.android.refrigeratorsurprise.data.RecipeDetailsResponse
import com.siddapps.android.refrigeratorsurprise.network.APIClient
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RecipePresenterImpl @Inject constructor(private val apiClient: APIClient, private val apiClientCoroutines: APIClient) : RecipePresenter {
    val TAG = "RecipePresenterImpl"
    lateinit var recipeView: RecipeView
    lateinit var disposable: CompositeDisposable

    override fun start(recipeView: RecipeView) {
        this.recipeView = recipeView
        this.disposable = CompositeDisposable()
    }

    override fun getRecipeList(ingredients: String, success: (RecipeDetailsResponse, Int) -> Unit) {
        recipeView.showProgress()

        apiClient.getRecipeList(ingredients,
                subscribe = {
                    disposable.add(it)
                }, success = {
            recipeView.hideProgress()
            recipeView.displayRecipes(it)
//            getRecipeDetails(it.recipes,success)
        }, failure = {
            recipeView.hideProgress()
        })
    }

    override fun getRecipeDetails(list: MutableList<Recipe>, success: (RecipeDetailsResponse, Int) -> Unit) {
        //chirecipeView.showProgress()
        for ((i, recipe) in list.withIndex()) {
            GlobalScope.async(Dispatchers.Default, CoroutineStart.DEFAULT, null, {
                val result = apiClientCoroutines.getRecipeId(recipe.recipeID).await()
                success.invoke(result, i)
            })
        }
    }

    override fun getRecipeHtml(url: String, success: (String?) -> Unit) {
        val result = apiClient.getRecipeHtml(url).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                success.invoke(t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                success.invoke(response.body()?.string())
            }

        })
    }

    override fun stop() {
        disposable.clear()
    }

}