package com.dentapp.app.di

import com.dentapp.app.data.api.ApiService
import com.dentapp.app.data.repository.AiRepository
import com.dentapp.app.data.repository.AuthRepository
import com.dentapp.app.data.repository.DoctorRepository
import com.dentapp.app.utils.TokenStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides @Singleton
    fun provideAuthRepository(api: ApiService, tokenStore: TokenStore): AuthRepository =
        AuthRepository(api, tokenStore)

    @Provides @Singleton
    fun provideDoctorRepository(api: ApiService): DoctorRepository =
        DoctorRepository(api)

    @Provides @Singleton
    fun provideAiRepository(api: ApiService): AiRepository =
        AiRepository(api)
}
