package com.siddapps.android.refrigeratorsurprise.ui.recipe

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.siddapps.android.refrigeratorsurprise.R
import com.siddapps.android.refrigeratorsurprise.data.Recipe
import com.squareup.picasso.Picasso

class RecipeAdapter(private val context: Context, private val recipes: List<Recipe>, private val listener: OnRecipeClickListener) :
        RecyclerView.Adapter<RecipeAdapter.RecipeHolder>() {
    private val TAG = "RecipeAdapter"

    override fun getItemCount(): Int {
         return recipes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecipeHolder {
        val v: View = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false)
        return RecipeHolder(v,listener)
    }

    override fun onBindViewHolder(holder: RecipeHolder?, position: Int) {
        holder?.bind(recipes[position])
    }


    class RecipeHolder(view: View, private val listener: OnRecipeClickListener) : RecyclerView.ViewHolder(view),View.OnClickListener {
        private val recipeImage: ImageView = view.findViewById(R.id.recipe_image)
        private val recipeTitle: TextView = view.findViewById(R.id.recipe_title)
        lateinit var recipe:Recipe
        private val TAG = "RecipeHolder"

        init {
            view.setOnClickListener(this)
        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe
            val url = recipe.imageURL.substring(0,4) + "s" + recipe.imageURL.substring(4)
            Picasso.with(itemView.context).load(url).into(recipeImage)
            recipeTitle.text = recipe.title
        }

        override fun onClick(v: View?) {
            listener.onRecipeClick(recipe)
        }
    }
}