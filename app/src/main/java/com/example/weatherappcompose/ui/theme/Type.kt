package com.example.weatherappcompose.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherappcompose.R

// Set of Material typography styles to start with
val SevenSegment = FontFamily(
    Font(R.font.seven_segment, FontWeight.Normal)
)

val SpaceMono = FontFamily(
    Font(R.font.space_mono_regular, FontWeight.Normal),
    Font(R.font.space_mono_bold, FontWeight.Bold)
)

val Typography = Typography(
    labelLarge = TextStyle(
        fontFamily = SpaceMono,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = SevenSegment,
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 2.sp
    ),
    titleLarge = TextStyle(
        fontFamily = SevenSegment,
        fontSize = 72.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 2.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = SpaceMono,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 2.sp
    )

//    bodyLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    )
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