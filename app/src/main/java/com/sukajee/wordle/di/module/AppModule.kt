package com.sukajee.wordle.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.sukajee.wordle.db.WordleDao
import com.sukajee.wordle.db.WordleDatabase
import com.sukajee.wordle.repository.BaseRepository
import com.sukajee.wordle.repository.WordleRepository
import com.sukajee.wordle.util.SHARED_PREFS
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
    fun provideAppDatabase(context: Context): WordleDatabase =
        Room.databaseBuilder(
            context,
            WordleDatabase::class.java,
            "wordle-database"
        ).build()

    @Provides
    @Singleton
    fun provideDao(database: WordleDatabase): WordleDao = database.dao()

    @Provides
    @Singleton
    fun provideWordRepository(context: Context, dao: WordleDao): BaseRepository = WordleRepository(context, dao)

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
}
