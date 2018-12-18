package com.siddapps.android.refrigeratorsurprise.ui.fragments.recipe

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
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class RecipeAdapter(private val context: Context, private var recipes: MutableList<Recipe>, private val listener: OnRecipeClickListener) :
        RecyclerView.Adapter<RecipeAdapter.RecipeHolder>() {
    private val TAG = "RecipeAdapter"

    override fun getItemCount(): Int {
         return recipes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecipeHolder {
        val v: View = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false)
        return RecipeHolder(v,listener) {
            recipes.removeAt(it)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: RecipeHolder?, position: Int) {
        holder?.bind(recipes[position])
    }

    fun update(recipes: MutableList<Recipe>) {
        this.recipes = recipes
        notifyDataSetChanged()
    }


    class RecipeHolder(view: View, private val listener: OnRecipeClickListener, val onImageError: (Int)-> Unit) : RecyclerView.ViewHolder(view),View.OnClickListener {
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
            Picasso.with(itemView.context).load(url).into(recipeImage, object : Callback {
                override fun onSuccess() {
                    Log.e("Tag", "success " + recipe.title)
                }

                override fun onError() {
                    Log.e("Tag", "failure " + recipe.title)
                    onImageError.invoke(adapterPosition)
                }
            })
            recipeTitle.text = recipe.title
        }

        override fun onClick(v: View?) {
            listener.onRecipeClick(recipe)
        }
    }
}