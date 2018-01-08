package com.siddapps.android.refrigeratorsurprise.dagger

import com.siddapps.android.refrigeratorsurprise.ui.ingredients.IngredientsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,PresenterModule::class])
interface AppComponent {
    fun inject(target: IngredientsActivity)
}