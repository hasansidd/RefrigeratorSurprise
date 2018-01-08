package com.siddapps.android.refrigeratorsurprise.ui.recipe

import android.accounts.NetworkErrorException
import android.util.Log
import com.siddapps.android.refrigeratorsurprise.data.RecipeResponse
import com.siddapps.android.refrigeratorsurprise.network.APIClient
import rx.Subscription
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class RecipePresenterImpl @Inject constructor(private val apiClient: APIClient) : RecipePresenter {
    val TAG = "RecipePresenterImpl"
    lateinit var recipeView: RecipeView
    lateinit var subscriptions: CompositeSubscription

    override fun start(recipeView: RecipeView) {
        this.recipeView = recipeView
        this.subscriptions = CompositeSubscription()
    }

    override fun getRecipeList(ingredients: String) {
        val subscription: Subscription = apiClient.getRecipeList(ingredients, object : APIClient.GetRecipeListCallback {
            override fun onSuccess(recipeResponse: RecipeResponse) {
                Log.e(TAG, "success")
                recipeView.displayRecipes(recipeResponse)
            }

            override fun onError(e: Throwable?) {
                Log.e(TAG, "error")
            }

        })

        subscriptions.add(subscription)
    }

    override fun stop() {
        subscriptions.unsubscribe()
    }

}