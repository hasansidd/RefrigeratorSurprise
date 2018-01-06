package com.siddapps.android.refrigeratorsurprise.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.siddapps.android.refrigeratorsurprise.R
import com.siddapps.android.refrigeratorsurprise.data.RecipeDetailsListing
import com.siddapps.android.refrigeratorsurprise.data.RecipeListing
import com.siddapps.android.refrigeratorsurprise.network.APIClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    val apiClient = APIClient()
    val id = -1
    lateinit var recipeListing:RecipeListing

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ingredients: List<String> = listOf("chicken","bacon")
        searchByIngredients(ingredients)
    }

    fun searchByIngredients(ingredients:List<String>) {
        val thing = apiClient.searchByIngredients(ingredients)

        thing.enqueue(object : Callback<RecipeListing> {
            override fun onResponse(call: Call<RecipeListing>?, response: Response<RecipeListing>?) {
                Log.e(TAG, response.toString())
                recipeListing = response?.body()!!
                val id = recipeListing?.recipes?.get(10)?.recipeID
                Log.e(TAG, recipeListing?.recipes?.get(10)?.title + " : " + recipeListing?.recipes?.get(10)?.recipeID)
               // getRecipeById(id!!)
                setRecylerView()
            }

            override fun onFailure(call: Call<RecipeListing>?, t: Throwable?) {
                t?.printStackTrace()
            }

        })
    }

    private fun setRecylerView() {
        recipe_recyclerview.layoutManager = GridLayoutManager(this,2)
        recipe_recyclerview.adapter=RecipeAdapter(this,recipeListing.recipes)
    }

    fun getRecipeById(id:String) {
        val call = apiClient.getRecipeById(id)
        call.enqueue(object : Callback<RecipeDetailsListing> {
            override fun onResponse(call: Call<RecipeDetailsListing>?, response: Response<RecipeDetailsListing>?) {
                Log.e(TAG, response!!.body().toString())
            }

            override fun onFailure(call: Call<RecipeDetailsListing>?, t: Throwable?) {
                t?.printStackTrace()
            }

        })
    }
}
