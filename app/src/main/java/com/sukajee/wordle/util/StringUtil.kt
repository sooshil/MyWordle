package com.sukajee.wordle.util

import com.sukajee.wordle.R

object Strings {
    fun getPositiveStrings() = listOf(
        R.string.bravo,
        R.string.excellent,
        R.string.exceptional,
        R.string.brilliant,
        R.string.outstanding,
        R.string.superb,
        R.string.fantastic,
        R.string.incredible,
        R.string.amazing,
        R.string.remarkable,
        R.string.impressive,
        R.string.splendid,
        R.string.awesome,
        R.string.great_work,
        R.string.great_job,
        R.string.hats_off,
        R.string.well_done,
        R.string.magnificent,
        R.string.phenomenal,
        R.string.extraordinary,
        R.string.spectacular,
        R.string.marvelous,
        R.string.you_won,
        R.string.inspiring,
        R.string.nailed_it
    ).random()

    fun getNegativeStrings() = listOf(
        R.string.oh_no,
        R.string.oops,
        R.string.you_lost,
        R.string.oh_dear,
        R.string.unfortunate,
        R.string.disappointing,
        R.string.failure,
        R.string.defeat,
        R.string.missed_the_mark,
        R.string.almost,
        R.string.insufficient,
        R.string.oh_well,
        R.string.lost_it,
        R.string.frustrating,
        R.string.poor_effort,
        R.string.uninspiring
    ).random()

    fun wordFoundMessage() = listOf(
        R.string.correct_word_found_variant_zero,
        R.string.correct_word_found_variant_one,
        R.string.correct_word_found_variant_two,
        R.string.correct_word_found_variant_three,
        R.string.correct_word_found_variant_four,
        R.string.correct_word_found_variant_five,
        R.string.correct_word_found_variant_six,
        R.string.correct_word_found_variant_seven
    ).random()

    fun wordNotFoundMessage() = listOf(
        R.string.word_not_found_variant_one,
        R.string.word_not_found_variant_two,
        R.string.word_not_found_variant_three,
        R.string.word_not_found_variant_four,
        R.string.word_not_found_variant_five
    ).random()
}
