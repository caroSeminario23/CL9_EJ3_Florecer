package com.example.florecer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.florecer.R

val NerkoOne = FontFamily(
    Font(R.font.nerko_one_regular)
)

val CormorantGaramond = FontFamily(
    Font(R.font.cormorant_garamond_light_italic)
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = NerkoOne,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp
    ),

    displayMedium = TextStyle(
        fontFamily = NerkoOne,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),

    labelSmall = TextStyle(
        fontFamily = CormorantGaramond,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = CormorantGaramond,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)