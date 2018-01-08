package com.siddapps.android.refrigeratorsurprise.ui.ingredients

interface IngredientsView {
    fun displayIngredients(ingredientsList: List<String>)

    fun updateIngredients(ingredientsList: List<String>)
}