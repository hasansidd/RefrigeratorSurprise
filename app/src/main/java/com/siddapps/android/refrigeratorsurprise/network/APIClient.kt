package com.siddapps.android.refrigeratorsurprise.network

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.siddapps.android.refrigeratorsurprise.data.RecipeDetailsResponse
import com.siddapps.android.refrigeratorsurprise.data.RecipeResponse
import com.siddapps.android.refrigeratorsurprise.utils.Const
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
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
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return retrofit.create(APIInterface::class.java)
        }

        fun getRetrofitCoroutines(): APIInterface {
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                    .baseUrl(Const.BASE_URL)
                    .client(APIClient.getOkHttpClient().build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .build()
            return retrofit.create(APIInterface::class.java)
        }
    }

    fun getRecipeList(ingredients: String, subscribe: (Disposable)-> Unit, success: (RecipeResponse)->Unit, failure: (String)->Unit) {
        return apiInterface.searchRecipesByIngredients(ingredients)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<RecipeResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                        subscribe.invoke(d)
                    }

                    override fun onNext(recipeResponse: RecipeResponse) {
                        success.invoke(recipeResponse)
                    }

                    override fun onError(e: Throwable) {
                        failure.invoke(e.localizedMessage)
                    }
                })
    }

    fun getRecipeId(id: String): Deferred<RecipeDetailsResponse> {
        return apiInterface.getRecipeById(id)
    }


    interface GetRecipeListCallback {
        fun onSuccess(recipeResponse: RecipeResponse)

        fun onError(e: Throwable?)
    }

}