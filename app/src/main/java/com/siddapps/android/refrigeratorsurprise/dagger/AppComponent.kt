package com.siddapps.android.refrigeratorsurprise.dagger

import com.siddapps.android.refrigeratorsurprise.network.APIClient
import com.siddapps.android.refrigeratorsurprise.network.APIInterface
import com.siddapps.android.refrigeratorsurprise.ui.ingredients.IngredientsActivity
import com.siddapps.android.refrigeratorsurprise.ui.recipe.RecipeActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,PresenterModule::class,NetworkModule::class])
interface AppComponent {
    fun inject(target: IngredientsActivity)

    fun inject(target: RecipeActivity)

    fun apiClient(): APIClient

}