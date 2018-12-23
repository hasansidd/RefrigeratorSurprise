package com.siddapps.android.refrigeratorsurprise.ui.fragments.recipedetails

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.siddapps.android.refrigeratorsurprise.HtmlParser
import com.siddapps.android.refrigeratorsurprise.R
import com.siddapps.android.refrigeratorsurprise.data.RecipeDetailItem
import com.siddapps.android.refrigeratorsurprise.data.RecipeDetails
import com.siddapps.android.refrigeratorsurprise.ui.MainActivity
import com.siddapps.android.refrigeratorsurprise.utils.httpToHttps
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_recipe_details.*
import kotlinx.android.synthetic.main.item_recipe.*


class RecipeDetailsFragment : Fragment() {
    companion object {
        val TAG = RecipeDetailsFragment.javaClass.simpleName
        private val EXTRA_TRANSITION_NAME_IMAGE = "transition_name_image"
        private val EXTRA_TRANSITION_NAME_TEXT = "transition_name_text"

        fun newInstance(recipeDetails: RecipeDetails?, transitionNameImage: String, transitionNameText: String): RecipeDetailsFragment {
            val bundle = Bundle()
            bundle.putString(EXTRA_TRANSITION_NAME_IMAGE, transitionNameImage)
            bundle.putString(EXTRA_TRANSITION_NAME_TEXT, transitionNameText)

            val fragment = RecipeDetailsFragment()
            fragment.recipeDetails = recipeDetails
            fragment.arguments = bundle
            return fragment
        }
    }

    var recipeDetails: RecipeDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
            exitTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_recipe_details, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        (activity as MainActivity).setHeaderTitle(recipeDetails?.name!!)
        collpasing_toolbar.title = recipeDetails?.name


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val transitionNameImage = arguments.getString(EXTRA_TRANSITION_NAME_IMAGE)
            imageview.transitionName = transitionNameImage
        }

        Picasso.with(context).load(recipeDetails?.imageUrl?.httpToHttps()).into(imageview, object : Callback {
            override fun onSuccess() {
                startPostponedEnterTransition()
            }

            override fun onError() {
                startPostponedEnterTransition()
            }

        })

        recycler_view.layoutManager = LinearLayoutManager(context)
        val strings = mutableListOf<RecipeDetailItem>()
        strings.add(RecipeDetailItem(HtmlParser.PREP_TIME, true))
        strings.add(RecipeDetailItem(recipeDetails?.timings?.get(HtmlParser.PREP_TIME)!! + " Minutes"))
        strings.add(RecipeDetailItem(HtmlParser.COOK_TIME, true))
        strings.add(RecipeDetailItem(recipeDetails?.timings?.get(HtmlParser.COOK_TIME)!! + " Minutes"))
        strings.add(RecipeDetailItem("Serving Size", true))
        strings.add(RecipeDetailItem(recipeDetails?.serving!!))
        strings.add(RecipeDetailItem("Ingredients", true))
        for (ingredient in recipeDetails?.ingredients!!) {
            strings.add(RecipeDetailItem(ingredient))
        }
        strings.add(RecipeDetailItem("Directions", true))
        for (direction in recipeDetails!!.instructions) {
            strings.add(RecipeDetailItem(direction))
        }
        recycler_view.adapter = RecipeDetailAdapter(context, strings)
    }

}


