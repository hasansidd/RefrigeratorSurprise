package com.siddapps.android.refrigeratorsurprise.ui.fragments.ingredients

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.siddapps.android.refrigeratorsurprise.R
import com.siddapps.android.refrigeratorsurprise.ui.MainActivity
import com.siddapps.android.refrigeratorsurprise.ui.fragments.recipe.RecipeFragment
import com.siddapps.android.refrigeratorsurprise.utils.add
import com.siddapps.android.refrigeratorsurprise.utils.hideKeyboard
import com.siddapps.android.refrigeratorsurprise.utils.pulse
import kotlinx.android.synthetic.main.fragment_ingredients.*


class IngredientsFragment : Fragment(), IngredientsView {
    companion object {
        val TAG = "Ingredients"
    }

    var presenter: IngredientsPresenter = IngredientsPresenterImpl()
    val ingredients: MutableList<String> = mutableListOf()
    val listener = object : IngredientsAdapter.OnIngredientsClicked {
        override fun onIngredientClicked(ingredient: String) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = activity as MainActivity
        activity.setIngredientsChecked()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_ingredients, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setHeaderTitle("Ingredients")
        ingredients_edit_text.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (ingredients_edit_text.text.toString().contains("add ")) {
                    val num = ingredients_edit_text.text.toString().subSequence(4, ingredients_edit_text.text.toString().length).toString()
                    try {
                        val numString: Int = num.toInt()
                        for (i in 0..numString) {
                            presenter.updateList("Ingredient $i")
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, e.toString())
                    }
                } else {
                    presenter.updateList(ingredients_edit_text.text.toString())
                }
                ingredients_edit_text.setText("")
                true
            } else {
                false
            }
        }

        presenter.start(this, ingredients)
        presenter.updateList("chicken")
        displayIngredients(ingredients)

        fab.setOnClickListener {
            fab.hideKeyboard()
            if (ingredients.isEmpty()) {
                val animLarge = animateTextSizeChange(add_ingredients_text_view, 1.2f)
                animLarge.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(p0: Animator?) {
                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        animateTextSizeChange(add_ingredients_text_view, 1.0f).start()
                    }

                    override fun onAnimationCancel(p0: Animator?) {
                    }

                    override fun onAnimationStart(p0: Animator?) {
                    }
                })
                animLarge.start()
//                add_ingredients_text_view.pulse(1.2f, 200)
            } else {
                fragmentManager.add {
                    this.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                    add(R.id.container, RecipeFragment.newInstance(ingredients = ingredients.joinToString(",")))
                    addToBackStack(RecipeFragment.TAG)
                }
            }
        }
        ingredients_edit_text.clearFocus()
        ingredients_edit_text.hideKeyboard()

        val itemTouchCallback = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
                return makeMovementFlags(0, ItemTouchHelper.RIGHT)
            }

            override fun isItemViewSwipeEnabled(): Boolean {
                return true
            }

            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                viewHolder?.adapterPosition?.let {
                    ingredients.removeAt(it)
                    updateIngredients(ingredients)
                }
            }

        }
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(ingredients_rv)
        setEmptyViewIfNeeded()
    }

    private fun animateTextSizeChange(view: View, scale: Float): ObjectAnimator {
        val xScale = PropertyValuesHolder.ofFloat("scaleX", scale)
        val yScale = PropertyValuesHolder.ofFloat("scaleY", scale)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, xScale, yScale)
        anim.duration = 200
        return anim
    }

    override fun displayIngredients(ingredientsList: List<String>) {
        ingredients_rv.layoutManager = LinearLayoutManager(activity)
        ingredients_rv.adapter = IngredientsAdapter(ingredientsList, activity, listener)
    }

    override fun updateIngredients(ingredientsList: List<String>) {
        setEmptyViewIfNeeded()
        val adapter: IngredientsAdapter? = ingredients_rv.adapter as? IngredientsAdapter
        adapter?.update(ingredientsList)
    }

    private fun setEmptyViewIfNeeded() {
        if (ingredients.isEmpty()) {
            empty_view.visibility = View.VISIBLE
        } else {
            empty_view.visibility = View.GONE
        }
    }
}