package com.sukajee.wordle.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.sukajee.wordle.R

val PathWay = FontFamily(
    Font(R.font.pathway_extreme, weight = FontWeight.SemiBold),
    Font(R.font.pathway_extreme_medium, weight = FontWeight.Normal)
)

val AumniSans = FontFamily(
    Font(R.font.aumnisans_regular, weight = FontWeight.Normal),
    Font(R.font.aumnisans_mediumitalic, weight = FontWeight.Medium)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = PathWay,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    bodyMedium = TextStyle(
        fontFamily = PathWay,
        fontWeight = FontWeight.Normal,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    labelSmall = TextStyle(
        fontFamily = AumniSans,
        fontWeight = FontWeight.Medium
    ),
    labelMedium = TextStyle(
        fontFamily = AumniSans,
        fontWeight = FontWeight.Normal
    )
)
