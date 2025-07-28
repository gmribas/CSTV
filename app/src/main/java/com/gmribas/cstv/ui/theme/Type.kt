package com.gmribas.cstv.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.gmribas.cstv.R

private val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val robotoFont = GoogleFont("Roboto")

val RobotoFontFamily = FontFamily(
    Font(googleFont = robotoFont, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = robotoFont, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = robotoFont, fontProvider = provider, weight = FontWeight.Bold)
)

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = FONT_SIZE_32,
        lineHeight = LINE_HEIGHT_28,
        letterSpacing = LETTER_SPACING_0
    ),
    headlineMedium = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = FONT_SIZE_22,
        lineHeight = LINE_HEIGHT_28,
        letterSpacing = LETTER_SPACING_0
    ),
    bodyLarge = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = FONT_SIZE_16,
        lineHeight = LINE_HEIGHT_24,
        letterSpacing = LETTER_SPACING_0_5
    ),
    bodyMedium = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = FONT_SIZE_13,
        lineHeight = LINE_HEIGHT_24,
        letterSpacing = LETTER_SPACING_0_5
    ),
    bodySmall = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = FONT_SIZE_11,
        lineHeight = LINE_HEIGHT_16,
        letterSpacing = LETTER_SPACING_0_5
    ),
    labelSmall = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = FONT_SIZE_10,
        lineHeight = LINE_HEIGHT_16,
        letterSpacing = LETTER_SPACING_0_5
    ),
    titleMedium = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = FONT_SIZE_16,
        lineHeight = LINE_HEIGHT_24,
        letterSpacing = LETTER_SPACING_0
    )
)
