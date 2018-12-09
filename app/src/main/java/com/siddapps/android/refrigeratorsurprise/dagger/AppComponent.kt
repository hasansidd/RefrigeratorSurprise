package com.siddapps.android.refrigeratorsurprise.dagger

import com.siddapps.android.refrigeratorsurprise.network.APIClient
import com.siddapps.android.refrigeratorsurprise.ui.MainActivity
import dagger.Component
import javax.inject.Singleton
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule


@Singleton
@Component(modules = [AppModule::class,PresenterModule::class,NetworkModule::class, AndroidInjectionModule::class, AndroidSupportInjectionModule::class])
interface AppComponent {
    fun inject(target: MainActivity)

    fun apiClient(): APIClient

}