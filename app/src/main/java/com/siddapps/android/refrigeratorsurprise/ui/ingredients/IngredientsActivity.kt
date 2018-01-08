package com.siddapps.android.refrigeratorsurprise.ui.ingredients

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.inputmethod.EditorInfo
import com.siddapps.android.refrigeratorsurprise.R
import com.siddapps.android.refrigeratorsurprise.application.RecipeApplication
import com.siddapps.android.refrigeratorsurprise.ui.recipe.RecipeActivity
import kotlinx.android.synthetic.main.activity_ingredients.*
import kotlinx.android.synthetic.main.item_ingredient.*
import javax.inject.Inject

class IngredientsActivity : AppCompatActivity(), IngredientsView {
    private val TAG = "IngredientsActivity"
    val ingredients: MutableList<String> = mutableListOf()

    @Inject
    lateinit var presenter: IngredientsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredients)
        (application as RecipeApplication).recipeComponent.inject(this)

        ingredients_edit_text.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                presenter.updateList(ingredients_edit_text.text.toString())
                ingredients_edit_text.setText("")
                true
            } else {
                false
            }
        }

        presenter.start(this, ingredients)
        displayIngredients(ingredients)

        fab.setOnClickListener({
            val i = RecipeActivity.newIntent(this, ingredients = ingredients.joinToString(","))
            startActivity(i)
        })
    }

    override fun displayIngredients(ingredientsList: List<String>) {
        ingredients_rv.layoutManager = LinearLayoutManager(this)
        ingredients_rv.adapter = IngredientsAdapter(ingredientsList, this)
    }

    override fun updateIngredients(ingredientsList: List<String>) {
        ingredients_rv.adapter = IngredientsAdapter(ingredientsList, this)
    }
}