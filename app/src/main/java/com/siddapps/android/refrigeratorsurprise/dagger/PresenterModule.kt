package com.siddapps.android.refrigeratorsurprise.dagger

import com.siddapps.android.refrigeratorsurprise.ui.fragments.ingredients.IngredientsPresenter
import com.siddapps.android.refrigeratorsurprise.ui.fragments.ingredients.IngredientsPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {
    @Provides
    @Singleton
    fun provideIngredientsPresenter(): IngredientsPresenter = IngredientsPresenterImpl()

//    @Provides
//    @Singleton
//    fun provideRecipePresenter(apiClient: APIClient): RecipePresenter = RecipePresenterImpl(apiClient)
}