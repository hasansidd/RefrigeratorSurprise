package com.siddapps.android.refrigeratorsurprise.ui.ingredients

class IngredientsPresenterImpl:IngredientsPresenter {
    lateinit var ingredientsView:IngredientsView

    override fun setView(ingredientsView: IngredientsView) {
        this.ingredientsView = ingredientsView
    }

}