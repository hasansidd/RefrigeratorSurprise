package com.siddapps.android.refrigeratorsurprise.data

import com.google.gson.annotations.SerializedName

data class RecipeDetailsResponse(
        @SerializedName("recipe") val recipe: RecipeDetails
)