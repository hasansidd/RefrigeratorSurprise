package com.siddapps.android.refrigeratorsurprise.ui.fragments.recipe

import android.view.View
import com.siddapps.android.refrigeratorsurprise.data.Recipe

interface OnRecipeClickListener {
    fun onRecipeClick(recipe: Recipe, view: View?)
}