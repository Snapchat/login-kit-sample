package com.waseem.snaploginkit.di

import android.content.Context
import com.snap.loginkit.SnapLogin
import com.snap.loginkit.SnapLoginProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AuthModule {

    @Singleton
    @Provides
    fun provideSnapLogin(
        @ApplicationContext context: Context
    ): SnapLogin = SnapLoginProvider.get(context)
}