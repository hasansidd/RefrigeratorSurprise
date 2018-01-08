package com.siddapps.android.refrigeratorsurprise.ui.recipe

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.siddapps.android.refrigeratorsurprise.R
import com.siddapps.android.refrigeratorsurprise.data.Recipe
import com.squareup.picasso.Picasso

class RecipeAdapter(private val context: Context, private val recipes: List<Recipe>) : RecyclerView.Adapter<RecipeAdapter.RecipeHolder>() {
    override fun getItemCount(): Int {
         return recipes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecipeHolder {
        val v: View = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false)
        return RecipeHolder(v)
    }

    override fun onBindViewHolder(holder: RecipeHolder?, position: Int) {
        holder?.bind(recipes[position])
    }

    class RecipeHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val recipeImage: ImageView = view.findViewById(R.id.recipe_image)
        private val recipeTitle: TextView = view.findViewById(R.id.recipe_title)

        fun bind(recipe: Recipe) {
            Picasso.with(itemView.context).load(recipe.imageURL).into(recipeImage)
            recipeTitle.text = recipe.title
        }
    }
}