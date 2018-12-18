package com.siddapps.android.refrigeratorsurprise.ui.fragments.recipe

import com.siddapps.android.refrigeratorsurprise.data.RecipeResponse

interface RecipeView {
    fun displayRecipes(recipeResponse: RecipeResponse)
    fun showProgress()
    fun hideProgress()
}