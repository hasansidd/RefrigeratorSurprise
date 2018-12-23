package com.siddapps.android.refrigeratorsurprise.application

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.siddapps.android.refrigeratorsurprise.dagger.AppComponent
import com.siddapps.android.refrigeratorsurprise.dagger.AppModule
import com.siddapps.android.refrigeratorsurprise.dagger.DaggerAppComponent

class RecipeApplication: Application() {
    lateinit var recipeComponent:AppComponent

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this);
        recipeComponent = initDagger(this)
    }

    fun initDagger(app:RecipeApplication):AppComponent =
            DaggerAppComponent.builder()
                    .appModule(AppModule(app))
                    .build()
}