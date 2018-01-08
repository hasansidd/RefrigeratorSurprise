package com.siddapps.android.refrigeratorsurprise.network

import android.util.Log
import com.siddapps.android.refrigeratorsurprise.data.RecipeResponse
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class APIClient @Inject constructor(private val apiInterface: APIInterface) {
    val TAG = "APIClient"

    fun getRecipeList(ingredients:String, callback:GetRecipeListCallback):Subscription {
        return apiInterface.searchRecipesByIngredients(ingredients)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Subscriber<RecipeResponse>() {
                    override fun onNext(recipeResponse: RecipeResponse) {
                        Log.e(TAG,recipeResponse.toString())
                        callback.onSuccess(recipeResponse)
                    }

                    override fun onError(e: Throwable?) {
                        Log.e(TAG,e.toString())
                        callback.onError(e)
                    }

                    override fun onCompleted() {
                        Log.e(TAG, "completed")
                    }

                })
    }


    interface GetRecipeListCallback {
        fun onSuccess(recipeResponse: RecipeResponse)

        fun onError(e:Throwable?)
    }

}