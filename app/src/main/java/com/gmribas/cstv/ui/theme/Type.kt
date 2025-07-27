package com.gmribas.cstv.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = FONT_SIZE_32,
        lineHeight = LINE_HEIGHT_28,
        letterSpacing = LETTER_SPACING_0
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = FONT_SIZE_22,
        lineHeight = LINE_HEIGHT_28,
        letterSpacing = LETTER_SPACING_0
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = FONT_SIZE_16,
        lineHeight = LINE_HEIGHT_24,
        letterSpacing = LETTER_SPACING_0_5
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = FONT_SIZE_16,
        lineHeight = LINE_HEIGHT_24,
        letterSpacing = LETTER_SPACING_0_5
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = FONT_SIZE_11,
        lineHeight = LINE_HEIGHT_16,
        letterSpacing = LETTER_SPACING_0_5
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = FONT_SIZE_10,
        lineHeight = LINE_HEIGHT_16,
        letterSpacing = LETTER_SPACING_0_5
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = FONT_SIZE_16,
        lineHeight = LINE_HEIGHT_24,
        letterSpacing = LETTER_SPACING_0
    )
)
