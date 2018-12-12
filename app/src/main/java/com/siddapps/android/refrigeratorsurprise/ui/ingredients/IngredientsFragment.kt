package com.siddapps.android.refrigeratorsurprise.ui.ingredients

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.siddapps.android.refrigeratorsurprise.R
import com.siddapps.android.refrigeratorsurprise.ui.recipe.RecipeFragment
import com.siddapps.android.refrigeratorsurprise.utils.add
import com.siddapps.android.refrigeratorsurprise.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_ingredients.*

class IngredientsFragment : Fragment(), IngredientsView {
    companion object {
        val TAG = "Ingredients"
    }
    var presenter: IngredientsPresenter = IngredientsPresenterImpl()
    val ingredients: MutableList<String> = mutableListOf()
    val listener = object : IngredientsAdapter.OnIngredientsClicked {
        override fun onIngredientClicked(ingredient: String) {

        }

        override fun onDeleteClicked(adapterPosition: Int) {
            ingredients.removeAt(adapterPosition)
            updateIngredients(ingredients)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.activity_ingredients, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ingredients_edit_text.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                presenter.updateList(ingredients_edit_text.text.toString())
                ingredients_edit_text.setText("")
                true
            } else {
                false
            }
        }

        enter_ingredient.setOnClickListener {
            presenter.updateList(ingredients_edit_text.text.toString())
            ingredients_edit_text.setText("")
        }

        presenter.start(this, ingredients)
        displayIngredients(ingredients)

        fab.setOnClickListener {
            fab.hideKeyboard()
            fragmentManager.add {
                add(R.id.container, RecipeFragment.newInstance(ingredients = ingredients.joinToString(",")))
                addToBackStack(RecipeFragment.TAG)
            }
        }
        ingredients_edit_text.clearFocus()
        ingredients_edit_text.hideKeyboard()
    }

    override fun displayIngredients(ingredientsList: List<String>) {
        ingredients_rv.layoutManager = LinearLayoutManager(activity)
        ingredients_rv.adapter = IngredientsAdapter(ingredientsList, activity, listener)
    }

    override fun updateIngredients(ingredientsList: List<String>) {
        ingredients_rv.adapter = IngredientsAdapter(ingredientsList, activity, listener)
    }
}