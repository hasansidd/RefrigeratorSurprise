package com.siddapps.android.refrigeratorsurprise.ui.fragments.recipe

import com.siddapps.android.refrigeratorsurprise.data.Recipe
import com.siddapps.android.refrigeratorsurprise.data.RecipeDetailsResponse

interface RecipePresenter {
    fun start(recipeView: RecipeView)

    fun getRecipeList(ingredients: String, success: (RecipeDetailsResponse, Int) -> Unit)

    fun getRecipeDetails(list: MutableList<Recipe>, success: (RecipeDetailsResponse, Int) -> Unit)

    fun getRecipeHtml(url: String, success: (String?) -> Unit)

    fun stop()
}