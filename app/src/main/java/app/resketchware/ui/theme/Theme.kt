package app.resketchware.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.LocalAbsoluteElevation
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

val primary = Color(0xff657CD9)

val backgroundDark = Color(0xff17181C)
val surfaceDark = Color(0xff212328)

val backgroundLight = Color(0xfff0f0f0)
val surfaceLight = Color(0xffffffff)

@Composable
fun Color.applyElevationOverlay(elevation: Dp = 0.dp): Color {
    val absoluteElevation = LocalAbsoluteElevation.current + elevation
    return MaterialTheme.colors.primary
        .copy(alpha = (absoluteElevation.value) / 100f)
        .compositeOver(this)
}

private val DarkColors = darkColors(
    primary = primary,
    secondary = primary,
    background = backgroundDark,
    surface = surfaceDark
)

private val LightColors = lightColors(
    primary = primary,
    secondary = primary,
    background = backgroundLight,
    surface = surfaceLight
)

@Composable
fun RsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = android.graphics.Color.TRANSPARENT
            window.navigationBarColor = android.graphics.Color.TRANSPARENT
            window.isStatusBarContrastEnforced = false
            window.isNavigationBarContrastEnforced = false

            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}
