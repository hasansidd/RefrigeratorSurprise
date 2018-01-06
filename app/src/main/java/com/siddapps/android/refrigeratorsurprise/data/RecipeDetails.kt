package com.siddapps.android.refrigeratorsurprise.data

import com.google.gson.annotations.SerializedName

data class RecipeDetails(
        @SerializedName("ingredients") val ingredients: List<String>
) {

    override fun toString(): String {
        var string = "\n"
        for (ingredient in ingredients) {
            string += ingredient + "\n"
        }
        return string
    }
}
