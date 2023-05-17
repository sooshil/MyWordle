package com.sukajee.wordle.repository

interface BaseRepository {
    suspend fun getAllWords(): List<String>
    suspend fun getTopWords(): List<String>
}
