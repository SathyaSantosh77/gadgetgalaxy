package com.example.gadgetgalaxy.di

import com.example.gadgetgalaxy.data.repository.AuthRepo
import com.example.gadgetgalaxy.data.repository.AuthRepoImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepoImpl): AuthRepo = impl
}