package com.example.savelifeapp.di

import com.example.savelifeapp.data.repository.AuthRepository
import com.example.savelifeapp.data.repository.AuthRepositoryImplement
import com.example.savelifeapp.data.repository.AccountRepositoryImpl
import com.example.savelifeapp.data.repository.AccountRespository
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
        auth: FirebaseAuth
    ): AccountRespository {
        return AccountRepositoryImpl(database, auth)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth,
    ): AuthRepository {
        return AuthRepositoryImplement(auth, database)
    }

}