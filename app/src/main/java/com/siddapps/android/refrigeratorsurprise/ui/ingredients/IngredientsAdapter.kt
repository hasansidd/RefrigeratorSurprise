package com.siddapps.android.refrigeratorsurprise.ui.ingredients

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.siddapps.android.refrigeratorsurprise.R
import com.siddapps.android.refrigeratorsurprise.data.Ingredient

class IngredientsAdapter(private val ingredients: List<String>, private val context:Context) :
        RecyclerView.Adapter<IngredientsAdapter.IngredientsHolder>() {

    override fun onBindViewHolder(holder: IngredientsHolder, position: Int) {
        holder.bind(ingredients[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): IngredientsHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.item_ingredient,parent,false)
        return IngredientsHolder(view)
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    class IngredientsHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ingredientTitle:TextView = view.findViewById(R.id.ingredient_title)

        fun bind(ingredient:String) {
            ingredientTitle.text = ingredient
        }
    }
}