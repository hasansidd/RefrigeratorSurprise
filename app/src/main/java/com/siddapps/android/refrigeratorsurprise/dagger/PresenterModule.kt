package com.siddapps.android.refrigeratorsurprise.dagger

import com.siddapps.android.refrigeratorsurprise.network.APIClient
import com.siddapps.android.refrigeratorsurprise.ui.ingredients.IngredientsPresenter
import com.siddapps.android.refrigeratorsurprise.ui.ingredients.IngredientsPresenterImpl
import com.siddapps.android.refrigeratorsurprise.ui.recipe.RecipePresenter
import com.siddapps.android.refrigeratorsurprise.ui.recipe.RecipePresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {
    @Provides
    @Singleton
    fun provideIngredientsPresenter(): IngredientsPresenter = IngredientsPresenterImpl()

    @Provides
    @Singleton
    fun provideRecipePresenter(apiClient: APIClient): RecipePresenter = RecipePresenterImpl(apiClient)
}