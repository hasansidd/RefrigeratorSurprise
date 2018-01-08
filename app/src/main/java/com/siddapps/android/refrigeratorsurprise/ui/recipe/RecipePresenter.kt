package com.siddapps.android.refrigeratorsurprise.ui.recipe

interface RecipePresenter {
    fun start(recipeView: RecipeView)

    fun getRecipeList(ingredients:String)

    fun stop()
}