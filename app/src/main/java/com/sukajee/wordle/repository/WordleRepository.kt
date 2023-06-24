package com.sukajee.wordle.repository

import android.content.Context
import android.util.Log
import com.sukajee.wordle.db.WordleDao
import com.sukajee.wordle.model.WordleEntry
import kotlinx.coroutines.flow.Flow
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "WordleRepository"

class WordleRepository @Inject constructor(
    private val context: Context,
    private val dao: WordleDao
) : BaseRepository {

    override suspend fun getAllWordsFromAsset(): List<String> =
        readFromAsset(context, "allwords.txt")

    override suspend fun getTopWordsFromAsset(): List<String> = readFromAsset(context, "words.txt")

    override fun getWord(): Flow<WordleEntry?> = dao.getWord()

    override suspend fun insertAllWords(words: List<WordleEntry>) = dao.insertAllWords(words)

    override suspend fun insertWord(word: WordleEntry) = dao.insertWord(word)

    override suspend fun deleteAllWords() = dao.deleteAllWords()

    override suspend fun getWonCount(): Int = dao.getWonCount()

    private fun readFromAsset(context: Context, fileName: String): List<String> {
        try {
            val inputStream: InputStream = context.assets.open(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val strings = mutableListOf<String>()

            // Use the reader as a resource that will be automatically closed
            reader.use {
                var line = reader.readLine()
                while (line != null) {
                    strings.add(line)
                    line = reader.readLine()
                }
            }
            return strings
        } catch (e: Exception) {
            Log.e(TAG, "getWords: Error fetching all words from asset.")
            return emptyList()
        }
    }
}
