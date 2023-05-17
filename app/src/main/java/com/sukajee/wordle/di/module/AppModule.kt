package com.sukajee.wordle.di.module

import android.app.Application
import android.content.Context
import com.sukajee.wordle.repository.BaseRepository
import com.sukajee.wordle.repository.WordleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideWordRepository(context: Context): BaseRepository = WordleRepository(context)
}
