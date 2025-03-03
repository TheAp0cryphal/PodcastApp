package com.audiobooks.podcasts.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

val AppTypography = Typography(
    titleSmall = Typography().titleSmall.copy(
        fontWeight = FontWeight.Bold,
    ),

    titleMedium = Typography().titleMedium.copy(
        fontWeight = FontWeight.SemiBold,
        color = Color.White
    ),

    bodySmall = Typography().bodySmall.copy(
        textAlign = TextAlign.Center,
        lineHeight = 14.sp,
        color = Color.Gray
    ),

    bodyMedium = Typography().bodyMedium.copy(
        fontStyle = FontStyle.Italic,
        color = Color.Gray
    ),

    bodyLarge = Typography().bodyLarge.copy(
        fontWeight = FontWeight.Bold,
        color = Color.Black
    ),

    headlineSmall = Typography().headlineSmall.copy(
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    ),

    headlineLarge = Typography().headlineLarge.copy(
        fontWeight = FontWeight.Bold
    )
)