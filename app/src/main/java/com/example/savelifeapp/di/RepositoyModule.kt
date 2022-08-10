package com.example.savelifeapp.di

import com.example.savelifeapp.data.repository.AuthRepository
import com.example.savelifeapp.data.repository.AuthRepositoryImplement
import com.example.savelifeapp.data.repository.ProfileRepositoryImpl
import com.example.savelifeapp.data.repository.ProfileRespository
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
    ): ProfileRespository {
        return ProfileRepositoryImpl(database)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth
    ): AuthRepository {
        return AuthRepositoryImplement(auth, database)
    }
}