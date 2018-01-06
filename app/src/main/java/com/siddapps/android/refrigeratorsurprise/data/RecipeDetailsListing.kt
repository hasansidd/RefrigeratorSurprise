package com.siddapps.android.refrigeratorsurprise.data

import com.google.gson.annotations.SerializedName

data class RecipeDetailsListing(
        @SerializedName("recipe") val recipe: RecipeDetails
)