package com.siddapps.android.refrigeratorsurprise.ui.recipe

import com.siddapps.android.refrigeratorsurprise.data.RecipeResponse

interface RecipeView {
    fun displayRecipes(recipeResponse: RecipeResponse)
}