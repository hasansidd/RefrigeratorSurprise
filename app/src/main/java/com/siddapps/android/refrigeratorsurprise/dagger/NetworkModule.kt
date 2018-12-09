package com.siddapps.android.refrigeratorsurprise.dagger

import com.google.gson.GsonBuilder
import com.siddapps.android.refrigeratorsurprise.network.APIClient
import com.siddapps.android.refrigeratorsurprise.network.APIInterface
import com.siddapps.android.refrigeratorsurprise.utils.Const
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideCall():Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .client(APIClient.getOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideAPIInterface(retrofit: Retrofit): APIInterface {
        return retrofit.create(APIInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideAPIClient(apiInterface: APIInterface): APIClient {
        return APIClient(apiInterface)
    }
}