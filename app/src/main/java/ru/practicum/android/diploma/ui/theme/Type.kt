package ru.practicum.android.diploma.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = DiplomaTypography(
    h1 = TextStyle(
        fontFamily = ypFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 38.sp,
        letterSpacing = 0.sp
    ),
    body22Medium = TextStyle(
        fontFamily = ypFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp
    ),
    body16Medium = TextStyle(
        fontFamily = ypFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.sp
    ),
    body16Regular = TextStyle(
        fontFamily = ypFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.sp
    ),
    body12Regular = TextStyle(
        fontFamily = ypFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    )
)

data class DiplomaTypography(
    val h1: TextStyle,
    val body22Medium: TextStyle,
    val body16Medium: TextStyle,
    val body16Regular: TextStyle,
    val body12Regular: TextStyle,
)
