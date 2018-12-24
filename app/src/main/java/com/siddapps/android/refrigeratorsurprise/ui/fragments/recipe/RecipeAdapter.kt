package com.siddapps.android.refrigeratorsurprise.ui.fragments.recipe

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.siddapps.android.refrigeratorsurprise.HtmlParser
import com.siddapps.android.refrigeratorsurprise.R
import com.siddapps.android.refrigeratorsurprise.data.Recipe
import com.siddapps.android.refrigeratorsurprise.utils.httpToHttps
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

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
        ViewCompat.setTransitionName(holder?.recipeImage, recipes[position].title)
        ViewCompat.setTransitionName(holder?.shadow, recipes[position].title+" shadow")
        ViewCompat.setTransitionName(holder?.recipeTitle, recipes[position].title+"text")

        holder?.bind(recipes[position])
    }

    fun update(recipes: MutableList<Recipe>?) {
        this.recipes = recipes!!
        notifyDataSetChanged()
    }

    class RecipeHolder(view: View, private val listener: OnRecipeClickListener, val onImageError: (Int)-> Unit) : RecyclerView.ViewHolder(view),View.OnClickListener {
        val container: View = view.findViewById(R.id.container)
        val recipeImage: ImageView = view.findViewById(R.id.recipe_image)
        val recipeTitle: TextView = view.findViewById(R.id.recipe_title)
        val shadow: View = view.findViewById(R.id.shadow)
        lateinit var recipe:Recipe
        private val TAG = "RecipeHolder"

        init {
            view.setOnClickListener(this)
        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe
            val url = recipe.imageURL.httpToHttps()
            Picasso.with(itemView.context).load(url).into(recipeImage, object : Callback {
                override fun onSuccess() {
                }

                override fun onError() {
                }
            })

            Picasso.with(itemView.context).load(url).into(object : Target {
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                }

                override fun onBitmapFailed(errorDrawable: Drawable?) {
                    onImageError.invoke(adapterPosition)
                }

                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    recipeImage.setImageBitmap(bitmap)
                    recipe.bitmap = bitmap!!
                }

            })


            recipeTitle.text = recipe.title
        }

        override fun onClick(v: View?) {
            listener.onRecipeClick(recipe, v)
        }
    }
}