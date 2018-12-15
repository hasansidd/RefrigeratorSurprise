package com.siddapps.android.refrigeratorsurprise.data

import com.google.gson.annotations.SerializedName

data class RecipeResponse(
        @SerializedName("count") val count: String,
        @SerializedName("recipes") val recipes: MutableList<Recipe>
)


