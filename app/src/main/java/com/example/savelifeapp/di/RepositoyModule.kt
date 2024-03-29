package com.example.savelifeapp.di

import android.content.Context
import com.example.savelifeapp.data.repository.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun provideProfileRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth,
        context: Context
    ): AccountRespository {
        return AccountRepositoryImpl(database, auth, context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth,
    ): AuthRepository {
        return AuthRepositoryImplement(auth, database)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth,
    ): HomeRepository {
        return HomeRepositotyImpl(auth, database)
    }

    @Provides
    @Singleton
    fun provideRequestRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth,
    ): RequestRepository {
        return RequestRepositoryImpl(auth, database)
    }
}