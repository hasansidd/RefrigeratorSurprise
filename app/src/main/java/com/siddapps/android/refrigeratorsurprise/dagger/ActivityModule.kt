package com.siddapps.android.refrigeratorsurprise.dagger

import com.siddapps.android.refrigeratorsurprise.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract fun provideMainActivity(): MainActivity
}