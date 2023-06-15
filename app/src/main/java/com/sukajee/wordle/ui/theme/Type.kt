package com.sukajee.wordle.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.sp
import com.sukajee.wordle.R

val PathWay = FontFamily(
    Font(R.font.pathway_extreme, weight = FontWeight.SemiBold),
    Font(R.font.pathway_extreme_medium, weight = FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = PathWay,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    bodyMedium = TextStyle(
        fontFamily = PathWay,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
