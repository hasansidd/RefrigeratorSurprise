package com.siddapps.android.refrigeratorsurprise.network

import com.google.gson.GsonBuilder
import com.siddapps.android.refrigeratorsurprise.data.RecipeResponse
import com.siddapps.android.refrigeratorsurprise.utils.Const
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class APIClient @Inject constructor(private val apiInterface: APIInterface) {
    companion object {
        fun getOkHttpClient(): OkHttpClient.Builder {
            var builder = OkHttpClient().newBuilder()
            builder.connectTimeout(30, TimeUnit.SECONDS)
            builder.readTimeout(30, TimeUnit.SECONDS)
            builder.writeTimeout(30, TimeUnit.SECONDS)

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            builder.interceptors().add(httpLoggingInterceptor)
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return builder
        }

        fun getRetrofit(): APIInterface {
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                    .baseUrl(Const.BASE_URL)
                    .client(APIClient.getOkHttpClient().build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
            return retrofit.create(APIInterface::class.java)
        }
    }

    fun getRecipeList(ingredients: String, callback: GetRecipeListCallback): Subscription {
        return apiInterface.searchRecipesByIngredients(ingredients)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<RecipeResponse>() {
                    override fun onNext(recipeResponse: RecipeResponse) {
                        callback.onSuccess(recipeResponse)
                    }

                    override fun onError(e: Throwable?) {
                        callback.onError(e)
                    }

                    override fun onCompleted() {
                    }

                })
    }


    interface GetRecipeListCallback {
        fun onSuccess(recipeResponse: RecipeResponse)

        fun onError(e: Throwable?)
    }

}