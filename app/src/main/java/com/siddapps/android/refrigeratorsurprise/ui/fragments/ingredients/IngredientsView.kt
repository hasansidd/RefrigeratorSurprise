package com.siddapps.android.refrigeratorsurprise.ui.fragments.ingredients

interface IngredientsView {
    fun displayIngredients(ingredientsList: List<String>)

    fun updateIngredients(ingredientsList: List<String>)
}