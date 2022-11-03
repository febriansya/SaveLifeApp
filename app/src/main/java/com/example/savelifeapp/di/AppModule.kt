package com.example.savelifeapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.savelifeapp.utils.SharedPrefConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideSharePref(@ApplicationContext context:Context):SharedPreferences{
        return context.getSharedPreferences(SharedPrefConstants.LOCAL_SHARED_PREF,Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideContext(application:Application):Context =application.applicationContext
}