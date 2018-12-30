package com.siddapps.android.refrigeratorsurprise

import com.siddapps.android.refrigeratorsurprise.data.RecipeDetails
import org.jsoup.Jsoup
import org.threeten.bp.Duration
import java.util.regex.Pattern

class HtmlParser {
    companion object {
        const val CLOSET_COOKING = "www.closetcooking.com"
        const val PIONEER_WOMAN = "thepioneerwoman.com"
        const val ALL_RECIPES = "allrecipes.com"

        const val PREP_TIME = "Prep Time"
        const val COOK_TIME = "Cook Time"

        val numberOnlyPattern = Pattern.compile("-?\\d+")

        fun parse(url: String): RecipeDetails? {
            val recipeDetails = RecipeDetails()

            val jsoup = Jsoup.connect(url)
            jsoup.get().run {
                when {
                    url.contains(CLOSET_COOKING) -> {
                        /**Ingredients**/
                        /**Ingredients**/
                        val ingredients = this.getElementsByClass("ingredients")
                        val ingredientArray = ingredients.tagName("recipeIngredient")[0].childNodes()
                        for ((i, node) in ingredientArray.withIndex()) {
//                        Log.e("TAG " + i, node.childNodes()[0].toString())
                            recipeDetails.ingredients.add(node.childNodes()[0].toString())
                        }

                        /**Instructions**/
                        /**Instructions**/
                        val instructions = this.getElementsByClass("instructions")
                        val instructionsArray = instructions.tagName("recipeInstructions")[0].childNodes()
                        for ((i, node) in instructionsArray.withIndex()) {
//                        Log.e("TAG " + i, node.childNodes()[0].toString())
                            recipeDetails.instructions.add(node.childNodes()[0].toString())
                        }

                        /**Timing**/
                        /**Timing**/
                        val details = this.getElementsByClass("details")
                        for ((i, detail) in details[0].getElementsByClass("time").withIndex()) {
                            var type = detail.childNodes()[0].childNodes()[0].toString()
                            val time = detail.childNodes()[3].toString()
                            val matcher = numberOnlyPattern.matcher(time)
                            while (matcher.find()) {
                                if (type.contains("prep")) {
                                    type = PREP_TIME
                                } else if (type.contains("cook")) {
                                    type = COOK_TIME
                                }
                                recipeDetails.timings[type] = matcher.group()
                            }
//                        Log.e("TAG " + i, "$type $time")
                        }

                        /**Serving**/
                        /**Serving**/
                        val servings = this.getElementsByClass("yield")
                        val string = servings.tagName("recipeYield")[0].childNodes()[2].childNodes()[0].toString()
                        val matcher = numberOnlyPattern.matcher(string)
                        while (matcher.find()) {
                            recipeDetails.serving = matcher.group()
                        }
//                    Log.e("TAG", recipeDetails.serving)
                    }
                    url.contains(PIONEER_WOMAN) -> {
                        /**Ingredients**/
                        /**Ingredients**/
                        val ingredientArray = this.select("span[itemprop=recipeIngredient]")
                        for ((i, ingredientNode) in ingredientArray.withIndex()) {
                            val ingredient = ingredientNode.childNodes()[0].toString()
//                            Log.e("TAG " + i, ingredient)
                            recipeDetails.ingredients.add(ingredient)
                        }

                        /**Instructions**/
                        /**Instructions**/
                        val instructionsArray = this.select("span[itemprop=recipeInstructions]")
                        for ((i, node) in instructionsArray.withIndex()) {
//                            Log.e("TAG " + i, node.childNodes()[0].toString())
                            recipeDetails.instructions.add(node.childNodes()[0].toString())
                        }

                        /**Timing**/
                        /**Timing**/
                        val cookTimeArray = this.select("span[itemprop=cookTime]")
                        val cookTime = cookTimeArray[0].childNodes()[0].toString()
                        val cookTimeParsed = Duration.parse(cookTime)
                        recipeDetails.timings[PREP_TIME] = cookTimeParsed.toMinutes().toString()

                        val prepTimeArray = this.select("span[itemprop=prepTime]")
                        val prepTime = prepTimeArray[0].childNodes()[0].toString()
                        val prepTimeParsed = Duration.parse(prepTime)
                        recipeDetails.timings[COOK_TIME] = prepTimeParsed.toMinutes().toString()


                        /**Serving**/
                        /**Serving**/
                        val servingArray = this.select("span[itemprop=recipeYield]")
                        val string = servingArray[0].childNodes()[0].toString()
                        val matcher = numberOnlyPattern.matcher(string)
                        while (matcher.find()) {
                            recipeDetails.serving = matcher.group()
                        }
                    }
                    url.contains(ALL_RECIPES) -> {
                        /**Ingredients**/
                        /**Ingredients**/
                        val ingredientArray = this.select("span[itemprop=recipeIngredient]")
                        for ((i, ingredientNode) in ingredientArray.withIndex()) {
                            val ingredient = ingredientNode.childNodes()[0].toString()
//                            Log.e("TAG " + i, ingredient)
                            recipeDetails.ingredients.add(ingredient)
                        }

                        /**Instructions**/
                        /**Instructions**/
                        val instructionsArray = this.select("span[class=recipe-directions__list--item]")
                        for ((i, node) in instructionsArray.withIndex()) {
//                            Log.e("TAG " + i, node.childNodes()[0].toString())
                            if (node.childNodes().size > 0) {
                                recipeDetails.instructions.add(node.childNodes()[0].toString())
                            }
                        }

                        /**Timing**/
                        /**Timing**/
                        val cookTimeArray = this.select("span[class=prepTime__item--time]")
                        val cookTime = cookTimeArray[0].childNodes()[0].toString()
//                        val cookTimeParsed = Duration.parse(cookTime)
                        recipeDetails.timings[PREP_TIME] = cookTime

                        val prepTime = cookTimeArray[1].childNodes()[0].toString()
//                        val prepTimeParsed = Duration.parse(prepTime)
                        recipeDetails.timings[COOK_TIME] = prepTime


                        /**Serving**/
                        /**Serving**/
                        val servingArray = this.select("div[class=subtext]")
                        val string = servingArray[0].childNodes()[0].toString()
                        val matcher = numberOnlyPattern.matcher(string)
                        while (matcher.find()) {
                            recipeDetails.serving = matcher.group()
                        }
                    }
                    else -> {
                        return null
                    }
                }
            }

            return recipeDetails
        }
    }

}
