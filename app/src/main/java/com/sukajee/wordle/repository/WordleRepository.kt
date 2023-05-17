package com.sukajee.wordle.repository

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception

private const val TAG = "WordleRepository"

class WordleRepository(private val context: Context) : BaseRepository {

    override suspend fun getAllWords(): List<String> = readFromAsset(context, "allwords.txt")
    override suspend fun getTopWords(): List<String> = readFromAsset(context, "words.txt")

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
