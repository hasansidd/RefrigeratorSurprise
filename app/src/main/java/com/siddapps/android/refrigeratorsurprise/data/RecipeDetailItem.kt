package com.siddapps.android.refrigeratorsurprise.data

data class RecipeDetailItem(val string: String, var isHeader: Boolean) {
    constructor(string: String) : this(string, false)
}