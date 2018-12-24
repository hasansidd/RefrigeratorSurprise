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
import com.siddapps.android.refrigeratorsurprise.utils.httpToHttps
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_recipe_details.*
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RecipeDetailsFragment : Fragment() {
    companion object {
        val TAG = RecipeDetailsFragment.javaClass.simpleName
        private val EXTRA_TRANSITION_NAME_IMAGE = "transition_name_image"
        private val EXTRA_TRANSITION_NAME_TEXT = "transition_name_text"
        private val EXTRA_TRANSITION_NAME_SHADOW = "transition_name_shadow"

        fun newInstance(recipeDetails: RecipeDetails?, transitionNameImage: String, transitionNameText: String, transitionNameShadow: String): RecipeDetailsFragment {
            val bundle = Bundle()
            bundle.putString(EXTRA_TRANSITION_NAME_IMAGE, transitionNameImage)
            bundle.putString(EXTRA_TRANSITION_NAME_TEXT, transitionNameText)
            bundle.putString(EXTRA_TRANSITION_NAME_SHADOW, transitionNameShadow)

            val fragment = RecipeDetailsFragment()
            fragment.recipeDetails = recipeDetails
            fragment.arguments = bundle
            return fragment
        }
    }

    var recipeDetails: RecipeDetails? = null
    var result: MutableList<RecipeDetailItem>? = null

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
        val shadow = view?.findViewById<View>(R.id.shadow)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val transitionNameImage = arguments.getString(EXTRA_TRANSITION_NAME_IMAGE)
            imageview.transitionName = transitionNameImage
//            val transitionNameText = arguments.getString(EXTRA_TRANSITION_NAME_TEXT)
//            shadow?.transitionName = transitionNameText
//            val transitionNameShadow = arguments.getString(EXTRA_TRANSITION_NAME_IMAGE)
//            shadow?.transitionName = transitionNameShadow
        }

        if (recipeDetails?.bitmap == null) {
            Picasso.with(context).load(recipeDetails?.imageUrl?.httpToHttps()).into(imageview, object : Callback {
                override fun onSuccess() {
                    startPostponedEnterTransition()
                }

                override fun onError() {
                    startPostponedEnterTransition()
                }

            })
        } else {
            startPostponedEnterTransition()
        }

        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = RecipeDetailAdapter(context, mutableListOf())

        start()

    }

    fun start() {
        GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT, null, {
            showProgress()
            recipeDetails = HtmlParser.parse(recipeDetails?.sourceUrl!!)
            val strings = mutableListOf<RecipeDetailItem>()
            if (recipeDetails?.timings != null && recipeDetails?.timings!!.isNotEmpty()) {
                strings.add(RecipeDetailItem(HtmlParser.PREP_TIME, true))
                strings.add(RecipeDetailItem(recipeDetails?.timings?.get(HtmlParser.PREP_TIME)!! + " Minutes"))
                strings.add(RecipeDetailItem(HtmlParser.COOK_TIME, true))
                strings.add(RecipeDetailItem(recipeDetails?.timings?.get(HtmlParser.COOK_TIME)!! + " Minutes"))
            }
            if (recipeDetails?.serving != null && recipeDetails?.serving!!.isNotEmpty()) {
                strings.add(RecipeDetailItem("Serving Size", true))
                strings.add(RecipeDetailItem(recipeDetails?.serving!!))
            }
            if (recipeDetails?.ingredients != null && recipeDetails?.ingredients!!.isNotEmpty()) {
                strings.add(RecipeDetailItem("Ingredients", true))
                for (ingredient in recipeDetails?.ingredients!!) {
                    strings.add(RecipeDetailItem(ingredient))
                }
            }
            if (recipeDetails?.instructions != null && recipeDetails?.instructions!!.isNotEmpty()) {
                strings.add(RecipeDetailItem("Directions", true))
                for (direction in recipeDetails!!.instructions) {
                    strings.add(RecipeDetailItem(direction))
                }
            }
            result = strings
            update()
        })
    }

    fun hideProgress() {
        progress_bar.visibility = View.GONE
    }

    private suspend fun update() {
       val adapter = recycler_view.adapter as RecipeDetailAdapter
        adapter.update(result!!)
    }

    fun showProgress() {
        progress_bar.visibility = View.VISIBLE
    }

}


