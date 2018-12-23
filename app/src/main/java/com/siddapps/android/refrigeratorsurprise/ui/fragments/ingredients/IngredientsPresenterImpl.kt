package com.siddapps.android.refrigeratorsurprise.ui.fragments.ingredients

import io.reactivex.disposables.CompositeDisposable

class IngredientsPresenterImpl:IngredientsPresenter {
    lateinit var ingredientsView:IngredientsView
    lateinit var ingredientList:MutableList<String>
    lateinit var disposable: CompositeDisposable


    override fun start(ingredientsView: IngredientsView, ingredientList: MutableList<String>) {
        this.ingredientsView = ingredientsView
        this.ingredientList = ingredientList
    }

    override fun updateList(ingredient: String) {
        ingredientList.add(0, ingredient)
        ingredientsView.updateIngredients(ingredientList)
    }

    override fun stop() {
        disposable.clear()
    }

}