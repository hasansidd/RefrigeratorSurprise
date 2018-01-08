package com.siddapps.android.refrigeratorsurprise.application

import android.app.Application
import com.siddapps.android.refrigeratorsurprise.dagger.AppComponent
import com.siddapps.android.refrigeratorsurprise.dagger.AppModule
import com.siddapps.android.refrigeratorsurprise.dagger.DaggerAppComponent

class RecipeApplication: Application() {
    lateinit var recipeComponent:AppComponent

    override fun onCreate() {
        super.onCreate()
        recipeComponent = initDagger(this)
    }

    fun initDagger(app:RecipeApplication):AppComponent =
            DaggerAppComponent.builder()
                    .appModule(AppModule(app))
                    .build()
}