package jano.net.proyecto_final.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


private val DarkColorScheme = darkColorScheme(
    background = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    primary = Color.LightGray,
    onPrimary = Color.Black
)

private val LightColorScheme = lightColorScheme(
    background = Color.White,
    surface = Color.Black,
    onSurface = Color.Black,
    primary = clWhite,
    onPrimary = Color.Black
)

@Composable
fun Proyecto_FinalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color es disponible solo en Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
