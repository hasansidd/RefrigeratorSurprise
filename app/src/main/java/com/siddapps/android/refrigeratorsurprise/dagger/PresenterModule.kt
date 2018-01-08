package com.siddapps.android.refrigeratorsurprise.dagger

import com.siddapps.android.refrigeratorsurprise.ui.ingredients.IngredientsPresenter
import com.siddapps.android.refrigeratorsurprise.ui.ingredients.IngredientsPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {
    @Provides
    @Singleton
    fun provideIngredientsPresenter(): IngredientsPresenter = IngredientsPresenterImpl()
}