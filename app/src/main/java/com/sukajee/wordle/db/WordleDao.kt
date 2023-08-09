package com.sukajee.wordle.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sukajee.wordle.model.WordleEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface WordleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWords(entries: List<WordleEntry>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(entry: WordleEntry)

    @Query("SELECT * FROM wordle_entries WHERE isSolved = 0 ORDER BY RANDOM() LIMIT 1")
    fun getWord(): Flow<WordleEntry>

    @Query("DELETE FROM wordle_entries")
    suspend fun deleteAllWords()

    @Query("SELECT COUNT(isSolved) FROM wordle_entries WHERE isSolved = 1")
    suspend fun getWonCount(): Int

    @Query("SELECT * FROM wordle_entries WHERE isSolved = 1")
    suspend fun getPlayedWordStat(): List<WordleEntry>
}
