package com.siddapps.android.refrigeratorsurprise.ui.recipe

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.siddapps.android.refrigeratorsurprise.R
import com.siddapps.android.refrigeratorsurprise.data.Recipe
import com.siddapps.android.refrigeratorsurprise.data.RecipeResponse
import com.siddapps.android.refrigeratorsurprise.network.APIClient
import com.siddapps.android.refrigeratorsurprise.ui.fragments.RecipeWebViewFragment
import com.siddapps.android.refrigeratorsurprise.utils.add
import kotlinx.android.synthetic.main.fragment_recipes.*

class RecipeFragment : Fragment(), RecipeView, OnRecipeClickListener {
    var presenter: RecipePresenter = RecipePresenterImpl(APIClient(APIClient.getRetrofit()))
    var ingredients: String? = null

    companion object {
        val TAG = "RecipeFragment"
        fun newInstance(ingredients: String): RecipeFragment {
            var fragment = RecipeFragment()
            fragment.ingredients = ingredients
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        presenter.start(this)
        super.onAttach(context)
    }

    override fun onStop() {
        presenter.stop()
        super.onStop()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (ingredients != null) {
            presenter.getRecipeList(ingredients!!)
        }
    }

    override fun onRecipeClick(recipe: Recipe) {
        fragmentManager.add {
            add(R.id.container, RecipeWebViewFragment.newInstance(recipe.sourceURL))
                    .addToBackStack(RecipeWebViewFragment.TAG)
        }
    }

    override fun displayRecipes(recipeResponse: RecipeResponse) {
        recipe_recyclerview.layoutManager = GridLayoutManager(activity, 2)
        recipe_recyclerview.adapter = RecipeAdapter(activity, recipeResponse.recipes, this)
    }

    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
        recipe_recyclerview.visibility = View.INVISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = View.INVISIBLE
        recipe_recyclerview.visibility = View.VISIBLE
    }
}