package com.siddapps.android.refrigeratorsurprise.ui.ingredients

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.siddapps.android.refrigeratorsurprise.R

class IngredientsAdapter(private var ingredients: List<String>, private val context: Context, private val listener: OnIngredientsClicked) :
        RecyclerView.Adapter<IngredientsAdapter.IngredientsHolder>() {
    private var lastPosition: Int = -1

    override fun onBindViewHolder(holder: IngredientsHolder, position: Int) {
        holder.bind(ingredients[position])
        if (position > lastPosition) {
            animateTextSizeChange(holder.ingredientTitle, 0.5f, 0).start()
            animateTextSizeChange(holder.ingredientTitle, 1f, 200).start()
            lastPosition = position
            return
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): IngredientsHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_ingredient, parent, false)
        return IngredientsHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    private fun animateTextSizeChange(view: View, scale: Float, duration: Long): ObjectAnimator {
        val xScale = PropertyValuesHolder.ofFloat("scaleX", scale)
        val yScale = PropertyValuesHolder.ofFloat("scaleY", scale)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, xScale, yScale)
        anim.duration = duration
        return anim
    }

    fun update(ingredients: List<String>) {
        this.ingredients = ingredients
        notifyDataSetChanged()
    }

    class IngredientsHolder(view: View, private val listener: OnIngredientsClicked) : RecyclerView.ViewHolder(view) {
        val ingredientTitle: TextView = view.findViewById(R.id.ingredient_title)

        fun bind(ingredient: String) {
            ingredientTitle.text = ingredient
            ingredientTitle.setOnClickListener {
                listener.onIngredientClicked(ingredient)
            }
        }
    }

    interface OnIngredientsClicked {
        fun onIngredientClicked(ingredient: String)
    }
}