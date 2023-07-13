package com.sukajee.wordle.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sukajee.wordle.model.WordleEntry

@Database(entities = [WordleEntry::class], version = 1, exportSchema = false)
abstract class WordleDatabase : RoomDatabase() {
    abstract fun dao(): WordleDao
}
