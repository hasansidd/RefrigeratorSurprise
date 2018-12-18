package com.siddapps.android.refrigeratorsurprise.ui.fragments.ingredients

interface IngredientsPresenter {

    fun start(ingredientsView: IngredientsView, ingredientList: MutableList<String>)

    fun updateList(ingredient:String)
}