package com.sukajee.wordle.repository

import com.sukajee.wordle.model.WordleEntry
import kotlinx.coroutines.flow.Flow

interface BaseRepository {
    fun getWord(): Flow<WordleEntry>
    suspend fun insertAllWords(words: List<WordleEntry>)
    suspend fun getAllWordsFromAsset(): List<String>
    suspend fun getTopWordsFromAsset(): List<String>
}
