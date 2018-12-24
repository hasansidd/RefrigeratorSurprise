package com.siddapps.android.refrigeratorsurprise.data

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

data class RecipeDetails(
        public @SerializedName("ingredients") var ingredients: MutableList<String>,
        public @SerializedName("instructions") var instructions: MutableList<String>,
        @SerializedName("timings") var timings: MutableMap<String, String>,
        @SerializedName("serving") var serving: String?,
        var name: String?,
        var imageUrl: String?,
        var sourceUrl: String?,
        val bitmap: Bitmap?

) {
    constructor() : this(mutableListOf<String>(), mutableListOf<String>(), mutableMapOf(), null, null, null, null,null)

    override fun toString(): String {
        var string = "\n"
        for (ingredient in ingredients) {
            string += ingredient + "\n"
        }
        return string
    }
}
