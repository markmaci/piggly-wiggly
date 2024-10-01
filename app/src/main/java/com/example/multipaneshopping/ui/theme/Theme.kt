package com.example.multipaneshopping.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val RedPiggy = Color(0xFFED1B24)
val BeigePiggy = Color(0xFFF5E1DA)
val White = Color(0xFFFFFFFF)

private val PiggyColorScheme = lightColorScheme(
    primary = RedPiggy,
    onPrimary = White,
    secondary = BeigePiggy,
    onSecondary = Color.Black,
    background = White,
    onBackground = Color.Black,
    surface = BeigePiggy,
    onSurface = Color.Black
)

@Composable
fun MultiPaneShoppingTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = PiggyColorScheme,
        typography = AppTypography,
        content = content
    )
}
