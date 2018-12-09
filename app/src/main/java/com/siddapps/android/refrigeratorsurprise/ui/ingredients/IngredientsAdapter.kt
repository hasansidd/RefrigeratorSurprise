package com.siddapps.android.refrigeratorsurprise.ui.ingredients

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.siddapps.android.refrigeratorsurprise.R

class IngredientsAdapter(private val ingredients: List<String>, private val context:Context, private val listener: OnIngredientsClicked) :
        RecyclerView.Adapter<IngredientsAdapter.IngredientsHolder>() {

    override fun onBindViewHolder(holder: IngredientsHolder, position: Int) {
        holder.bind(ingredients[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): IngredientsHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.item_ingredient,parent,false)
        return IngredientsHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    class IngredientsHolder(view: View, private val listener: OnIngredientsClicked) : RecyclerView.ViewHolder(view) {
        private val ingredientTitle:TextView = view.findViewById(R.id.ingredient_title)
        private val ingredientDeleteButton: ImageView = view.findViewById(R.id.ingredient_delete)

        fun bind(ingredient:String) {
            ingredientTitle.text = ingredient
            ingredientTitle.setOnClickListener {
                listener.onIngredientClicked(ingredient)
            }
            ingredientDeleteButton.setOnClickListener {
                listener.onDeleteClicked(adapterPosition)
            }
        }
    }

    interface OnIngredientsClicked {
        fun onIngredientClicked(ingredient: String)
        fun onDeleteClicked(adapterPosition: Int)
    }
}