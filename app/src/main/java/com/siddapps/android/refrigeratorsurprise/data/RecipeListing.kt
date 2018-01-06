package com.siddapps.android.refrigeratorsurprise.data

import com.google.gson.annotations.SerializedName

data class RecipeListing(
        @SerializedName("count") val count: String,
        @SerializedName("recipes") val recipes: List<Recipe>
)


