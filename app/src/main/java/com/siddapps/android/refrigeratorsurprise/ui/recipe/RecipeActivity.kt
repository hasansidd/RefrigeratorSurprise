package com.siddapps.android.refrigeratorsurprise.ui.recipe

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.widget.Toast
import com.siddapps.android.refrigeratorsurprise.R
import com.siddapps.android.refrigeratorsurprise.application.RecipeApplication
import com.siddapps.android.refrigeratorsurprise.data.Recipe
import com.siddapps.android.refrigeratorsurprise.data.RecipeResponse
import com.siddapps.android.refrigeratorsurprise.network.APIClient
import kotlinx.android.synthetic.main.activity_recipes.*
import javax.inject.Inject

class RecipeActivity : AppCompatActivity(),RecipeView, OnRecipeClickListener {
    private val TAG = "RecipeActivity"

    companion object {
        private val INGREDIENTS_EXTRA = "ingredients_extra"
        fun newIntent(context:Context, ingredients:String): Intent {
           val i = Intent(context, RecipeActivity::class.java)
            i.putExtra(INGREDIENTS_EXTRA, ingredients )
            return i
        }
    }

    @Inject
    lateinit var presenter:RecipePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)
        (application as RecipeApplication).recipeComponent.inject(this)
        presenter.start(this)

        val ingredients = intent.getStringExtra(INGREDIENTS_EXTRA)
        presenter.getRecipeList(ingredients)
    }

    override fun displayRecipes(recipeResponse: RecipeResponse) {
        recipe_recyclerview.layoutManager = GridLayoutManager(this, 2)
        recipe_recyclerview.adapter = RecipeAdapter(this, recipeResponse.recipes, this)
    }

    override fun onRecipeClick(recipe: Recipe) {
        Toast.makeText(this,recipe.title,Toast.LENGTH_LONG).show()
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }
}
