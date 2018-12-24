package com.siddapps.android.refrigeratorsurprise.ui.fragments.recipe

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.siddapps.android.refrigeratorsurprise.HtmlParser
import com.siddapps.android.refrigeratorsurprise.R
import com.siddapps.android.refrigeratorsurprise.data.Recipe
import com.siddapps.android.refrigeratorsurprise.data.RecipeDetails
import com.siddapps.android.refrigeratorsurprise.data.RecipeResponse
import com.siddapps.android.refrigeratorsurprise.network.APIClient
import com.siddapps.android.refrigeratorsurprise.ui.MainActivity
import com.siddapps.android.refrigeratorsurprise.ui.fragments.recipedetails.RecipeDetailsFragment
import com.siddapps.android.refrigeratorsurprise.utils.add
import com.siddapps.android.refrigeratorsurprise.utils.pulse
import kotlinx.android.synthetic.main.fragment_ingredients.*
import kotlinx.android.synthetic.main.fragment_recipes.*
import kotlinx.coroutines.*
import java.util.*


class RecipeFragment : Fragment(), RecipeView, OnRecipeClickListener {
    var presenter: RecipePresenter = RecipePresenterImpl(APIClient(APIClient.getRetrofit()), APIClient(APIClient.getRetrofitCoroutines()))
    var ingredients: String? = null
    var savedState: Bundle? = null
    var recipes: MutableList<Recipe>? = null
    var job: Job? = null

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
        if (savedState != null) {
            val array = savedState?.getParcelableArray("recipes")
            recipes = mutableListOf(array) as MutableList<Recipe>
        }
        return inflater?.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setHeaderTitle("Recipes")

        if (recipes != null && recipes!!.size > 0) {
            recipe_recyclerview.adapter = RecipeAdapter(activity, recipes!!, this)
            recipe_recyclerview.layoutManager = GridLayoutManager(activity, 2)
            val adapter = recipe_recyclerview.adapter as RecipeAdapter
            adapter.update(recipes)
        } else {
            if (ingredients != null) {
                presenter.getRecipeList(ingredients!!)
                { recipeIngredients, i ->
                    val adapter = recipe_recyclerview.adapter as RecipeAdapter
                    for (recipe in recipes!!) {
                        recipe.title = recipeIngredients.recipe.ingredients.toString()
                    }
                    adapter.update(recipes)
                }
            }
        }
    }

    override fun onRecipeClick(recipe: Recipe, view: View?) {
        val recipeDetails = RecipeDetails()
        recipeDetails?.name = recipe.title
        recipeDetails?.imageUrl = recipe.imageURL
        recipeDetails?.sourceUrl = recipe.sourceURL

        fragmentManager.add {
            val imageView: View? = view?.findViewById(R.id.recipe_image)
            val textView: View? = view?.findViewById(R.id.recipe_title)
            val shadowView: View? = view?.findViewById(R.id.shadow)

            val recipeDetailsFragment = RecipeDetailsFragment.newInstance(recipeDetails,
//                        ViewCompat.getTransitionName(imageView),
                    ViewCompat.getTransitionName(imageView),
                    ViewCompat.getTransitionName(textView),
                    ViewCompat.getTransitionName(shadowView))

            addSharedElement(view?.findViewById(R.id.recipe_image), ViewCompat.getTransitionName(imageView))
            addSharedElement(view?.findViewById(R.id.recipe_title), ViewCompat.getTransitionName(textView))
            addSharedElement(view?.findViewById(R.id.shadow), ViewCompat.getTransitionName(shadowView))
            addToBackStack(RecipeDetailsFragment::class.java.simpleName)
            replace(R.id.container, recipeDetailsFragment, tag)

        }

//        job = GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT, null, {
//            val recipeDetails = HtmlParser.parse(recipe.sourceURL)
//        })
    }

    override fun displayRecipes(recipeResponse: RecipeResponse) {
        this.recipes = recipeResponse.recipes
        setEmptyViewIfNeeded()

        if (recipeResponse.recipes.size > 0) {
            recipe_recyclerview.layoutManager = GridLayoutManager(activity, 2)

            var recipeRemove: MutableList<Recipe>? = null
            recipeRemove = mutableListOf()
            for (recipe in recipes!!) {
                if (!recipe.sourceURL.contains(HtmlParser.CLOSET_COOKING) &&
                        !recipe.sourceURL.contains(HtmlParser.PIONEER_WOMAN) &&
                        !recipe.sourceURL.contains(HtmlParser.ALL_RECIPES)) {
                    Log.e("removing URL", recipe.sourceURL)
                    recipeRemove.add(recipe)
                }
            }
            recipes!!.removeAll(recipeRemove)


            recipe_recyclerview.adapter = RecipeAdapter(activity, recipes!!, this)
        }

    }

    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
        recipe_recyclerview.visibility = View.INVISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = View.INVISIBLE
        recipe_recyclerview.visibility = View.VISIBLE
    }

    private fun setEmptyViewIfNeeded() {
        if (recipes!!.isEmpty()) {
            empty_view_recipe.visibility = View.VISIBLE
        } else {
            empty_view_recipe.visibility = View.GONE
        }
    }

    override fun onPause() {
        if (job != null) {
            job!!.cancel()
            Log.e("TAG", "cancled")
        } else {
            Log.e("TAG", "not canclled")
        }
        super.onPause()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        val arrayList: ArrayList<Recipe> = ArrayList()
        for (recipe in recipes!!) {
            arrayList.add(recipe)
        }
        savedState?.putParcelableArray("recipes", arrayList.toArray() as Array<Parcelable>?)
    }
}