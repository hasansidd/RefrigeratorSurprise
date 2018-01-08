package com.siddapps.android.refrigeratorsurprise.ui.ingredients

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.siddapps.android.refrigeratorsurprise.R
import kotlinx.android.synthetic.main.activity_ingredients.*

class IngredientsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredients)

        val ingredients = dummy()

        ingredients_rv.layoutManager = LinearLayoutManager(this)
        ingredients_rv.adapter = IngredientsAdapter(ingredients, this)
    }

    private fun dummy(): List<String> {
        return listOf(
                "Chicken",
                "Lamb",
                "Tomato")
    }
}