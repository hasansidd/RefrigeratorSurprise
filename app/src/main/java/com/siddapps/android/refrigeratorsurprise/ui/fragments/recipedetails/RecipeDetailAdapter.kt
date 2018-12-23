package com.siddapps.android.refrigeratorsurprise.ui.fragments.recipedetails

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.siddapps.android.refrigeratorsurprise.R
import com.siddapps.android.refrigeratorsurprise.data.RecipeDetailItem

class RecipeDetailAdapter(private val context:Context, private val items: MutableList<RecipeDetailItem>) : RecyclerView.Adapter<RecipeDetailAdapter.RecipeDetailViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecipeDetailViewHolder {
        val v: View?
        v = if (viewType == 1) {
            LayoutInflater.from(context).inflate(R.layout.item_recipe_detail, parent, false)
        } else {
            LayoutInflater.from(context).inflate(R.layout.item_recipe_detail_header, parent, false)
        }
        return RecipeDetailViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].isHeader) {
            0
        } else {
            1
        }
    }

    override fun onBindViewHolder(holder: RecipeDetailViewHolder?, position: Int) {
        holder?.bind(items!![position])
    }


    class RecipeDetailViewHolder(v: View?) : RecyclerView.ViewHolder(v) {
        var textView: TextView = v?.findViewById(R.id.textview)!!
        fun bind(recipeDetailItem: RecipeDetailItem) {
            textView.text = recipeDetailItem.string
        }
    }
}