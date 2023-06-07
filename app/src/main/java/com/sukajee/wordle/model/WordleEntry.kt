package com.sukajee.wordle.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wordle_entries")
data class WordleEntry(
    @PrimaryKey(autoGenerate = true)
    val entryId: Int = 0,
    val word: String,
    val isSolved: Boolean = false,
    val attempt: Int = -1
)
