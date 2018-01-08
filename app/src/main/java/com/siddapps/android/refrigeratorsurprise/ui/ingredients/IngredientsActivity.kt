package com.siddapps.android.refrigeratorsurprise.ui.ingredients

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.siddapps.android.refrigeratorsurprise.R
import com.siddapps.android.refrigeratorsurprise.application.RecipeApplication
import kotlinx.android.synthetic.main.activity_ingredients.*
import javax.inject.Inject

class IngredientsActivity : AppCompatActivity(), IngredientsView {

    @Inject
    lateinit var presenter: IngredientsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredients)
        (application as RecipeApplication).recipeComponent.inject(this)

        presenter.setView(this)
        val ingredients = dummy()

        ingredients_rv.layoutManager = LinearLayoutManager(this)
        ingredients_rv.adapter = IngredientsAdapter(ingredients, this)
    }

    private fun dummy(): List<String> {
        return listOf(
                "Chicken",
                "Lamb",
                "Tomato")
    }

    override fun displayIngredients() {
        TODO("not implemented")
    }
}