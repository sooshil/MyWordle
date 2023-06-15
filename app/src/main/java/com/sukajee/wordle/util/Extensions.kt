package com.sukajee.wordle.util

fun String.padWithZeros(desiredLength: Int = 3): String {
    val zerosToAdd = desiredLength - length
    return "0".repeat(zerosToAdd.coerceAtLeast(0)) + this
}
