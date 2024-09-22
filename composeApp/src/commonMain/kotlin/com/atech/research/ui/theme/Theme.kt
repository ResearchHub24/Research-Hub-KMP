package com.atech.research.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val SelectedChipContainerColor = Color(
    0xFFF3FFE6
)

val SelectedChipContainColor = Color(
    0xFF3C6C34
)

val ComputerDarkBackground = Color(
    0xFF1A1A1A
)

val ComputerLightBackground = Color(
    0xFFFFFFFF
)

val lightColorScheme = ColorScheme(
    background = Color(0xfff9f9f9),
    error = Color(0xffba1a1a),
    errorContainer = Color(0xffffdad6),
    inverseOnSurface = Color(0xfff1f1f1),
    inversePrimary = Color(0xff4fd8ea),
    inverseSurface = Color(0xff303030),
    onBackground = Color(0xff1b1b1b),
    onError = Color(0xffffffff),
    onErrorContainer = Color(0xff410002),
    onPrimary = Color(0xffffffff),
    onPrimaryContainer = Color(0xff001f24),
    onSecondary = Color(0xffffffff),
    onSecondaryContainer = Color(0xff051f23),
    onSurface = Color(0xff1b1b1b),
    onSurfaceVariant = Color(0xff474747),
    onTertiary = Color(0xffffffff),
    onTertiaryContainer = Color(0xff0e1b37),
    outline = Color(0xff777777),
    outlineVariant = Color(0xffb3c1c3),
    primary = Color(0xff006973),
    primaryContainer = Color(0xff94f1ff),
    scrim = Color(0xff000000),
    secondary = Color(0xff4a6266),
    secondaryContainer = Color(0xffcde7ec),
    surface = Color(0xfff9f9f9),
    surfaceTint = Color(0xff006973),
    surfaceVariant = Color(0xffe2e2e2),
    tertiary = Color(0xff525e7d),
    tertiaryContainer = Color(0xffd9e2ff),
    surfaceBright = Color(0xfff9f9f9),
    surfaceDim = Color(0xffdadada),
    surfaceContainer = Color(0xffeeeeee),
    surfaceContainerHigh = Color(0xffe8e8e8),
    surfaceContainerHighest = Color(0xffe2e2e2),
    surfaceContainerLow = Color(0xfff3f3f3),
    surfaceContainerLowest = Color(0xffffffff),
)

val darkColorScheme = ColorScheme(
    background = Color(0xff131313),
    error = Color(0xffffb4ab),
    errorContainer = Color(0xff93000a),
    inverseOnSurface = Color(0xff303030),
    inversePrimary = Color(0xff006973),
    inverseSurface = Color(0xffe2e2e2),
    onBackground = Color(0xffe2e2e2),
    onError = Color(0xff690005),
    onErrorContainer = Color(0xffffdad6),
    onPrimary = Color(0xff00363c),
    onPrimaryContainer = Color(0xff94f1ff),
    onSecondary = Color(0xff1c3438),
    onSecondaryContainer = Color(0xffcde7ec),
    onSurface = Color(0xffe2e2e2),
    onSurfaceVariant = Color(0xffc6c6c6),
    onTertiary = Color(0xff24304d),
    onTertiaryContainer = Color(0xffd9e2ff),
    outline = Color(0xff919191),
    outlineVariant = Color(0xff3d4e51),
    primary = Color(0xff4fd8ea),
    primaryContainer = Color(0xff004f57),
    scrim = Color(0xff000000),
    secondary = Color(0xffb1cbd0),
    secondaryContainer = Color(0xff334b4f),
    surface = Color(0xff131313),
    surfaceTint = Color(0xff4fd8ea),
    surfaceVariant = Color(0xff474747),
    tertiary = Color(0xffbac6ea),
    tertiaryContainer = Color(0xff3a4664),
    surfaceBright = Color(0xff393939),
    surfaceDim = Color(0xff131313),
    surfaceContainer = Color(0xff1f1f1f),
    surfaceContainerHigh = Color(0xff2a2a2a),
    surfaceContainerHighest = Color(0xff353535),
    surfaceContainerLow = Color(0xff1b1b1b),
    surfaceContainerLowest = Color(0xff0e0e0e),
)
val ColorScheme.captionColor: Color
    @Composable
    get() = onSurface.copy(alpha = 0.6f)


@Composable
fun ResearchHubTheme(
    isAndroid: Boolean = true,
    isDark: Boolean = isDark(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDark) darkColorScheme.let {
        if (!isAndroid) darkColorScheme.copy(
            surface = ComputerDarkBackground,
            background = ComputerDarkBackground
        ) else it
    } else lightColorScheme.let {
        if (!isAndroid) lightColorScheme.copy(
            surface = ComputerLightBackground,
            background = ComputerLightBackground
        ) else it
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
//        val view = LocalView.current
//        val color = LocalTheme.current.navBarColor.toArgb()
//        if (!view.isInEditMode) {
//            SideEffect {
//                val window = (view.context as Activity).window
//                window.navigationBarColor = color
//                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = isDark
//                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
//                    isDark
//            }
//        }
}