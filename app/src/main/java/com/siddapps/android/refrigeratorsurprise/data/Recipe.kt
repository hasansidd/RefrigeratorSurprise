package com.siddapps.android.refrigeratorsurprise.data

import com.google.gson.annotations.SerializedName

data class Recipe(
        @SerializedName("title") var title: String,
        @SerializedName("image_url") val imageURL: String,
        @SerializedName("source_url") val sourceURL: String,
        @SerializedName("recipe_id") val recipeID: String,
        val favorited: Boolean
) {
    override fun toString(): String {
        val string: String =
                "\nTitle:\t" + title + "\n" +
                        "Recipe ID:\t" + recipeID + "\n"
        "Image URL:\t" + imageURL + "\n" +
                "Source URL:\t" + sourceURL + "\n"

        return string
    }
}