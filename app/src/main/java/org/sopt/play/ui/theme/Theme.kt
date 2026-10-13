package org.sopt.play.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

private val DefaultSoptColors = SoptColors()
private val DefaultSoptTypography = SoptTypography()
private val DefaultSoptShapes = SoptShapes()

private val LocalSoptColors = staticCompositionLocalOf {
    DefaultSoptColors
}

private val LocalSoptTypography = staticCompositionLocalOf {
    DefaultSoptTypography
}

private val LocalSoptShapes = staticCompositionLocalOf {
    DefaultSoptShapes
}

object SoptTheme {
    val colors: SoptColors
    @Composable
    @ReadOnlyComposable
    get() = LocalSoptColors.current

    val typography: SoptTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalSoptTypography.current

    val shapes: SoptShapes
    @Composable
    @ReadOnlyComposable
    get() = LocalSoptShapes.current
}

@Composable
fun SoptTheme(
    colors: SoptColors = DefaultSoptColors,
    typography: SoptTypography = DefaultSoptTypography,
    shapes: SoptShapes = DefaultSoptShapes,
    content: @Composable () -> Unit
) {
    val materialColors = remember(colors) {
        colors.toMaterialColorScheme()
    }

    val materialTypography = remember(typography) {
        typography.toMaterialTypography()
    }

    val materialShapes = remember(shapes) {
        Shapes(
            extraSmall = shapes.input,
            small = shapes.input,
            medium = shapes.input,
            large = shapes.input,
            extraLarge = shapes.input
        )
    }

    CompositionLocalProvider(
        LocalSoptColors provides colors,
        LocalSoptTypography provides typography,
        LocalSoptShapes provides shapes
    ) {
        MaterialTheme(
            colorScheme = materialColors,
            typography = materialTypography,
            shapes = materialShapes,
            content = content
        )
    }
}

private fun SoptColors.toMaterialColorScheme(): ColorScheme =
    lightColorScheme(
        primary = buttonBackground,
        onPrimary = buttonContent,
        primaryContainer = disabledBackground,
        onPrimaryContainer = textPrimary,
        inversePrimary = textSecondary,

        secondary = textInput,
        onSecondary = background,
        secondaryContainer = disabledBackground,
        onSecondaryContainer = textLabel,

        tertiary = textInput,
        onTertiary = background,
        tertiaryContainer = disabledBackground,
        onTertiaryContainer = textLabel,

        background = background,
        onBackground = textPrimary,
        surface = background,
        onSurface = textLabel,
        surfaceVariant = disabledBackground,
        onSurfaceVariant = textInput,
        surfaceTint = buttonBackground,

        inverseSurface = textLabel,
        inverseOnSurface = buttonContent,

        error = error,
        onError = background,
        errorContainer = background,
        onErrorContainer = error,

        outline = borderDefault,
        outlineVariant = borderDefault,
        scrim = textPrimary,

        surfaceBright = background,
        surfaceDim = disabledBackground,
        surfaceContainer = disabledBackground,
        surfaceContainerHigh = disabledBackground,
        surfaceContainerHighest = disabledBackground,
        surfaceContainerLow = background,
        surfaceContainerLowest = background,

        primaryFixed = disabledBackground,
        primaryFixedDim = borderDefault,
        onPrimaryFixed = textPrimary,
        onPrimaryFixedVariant = textInput,

        secondaryFixed = disabledBackground,
        secondaryFixedDim = borderDefault,
        onSecondaryFixed = textLabel,
        onSecondaryFixedVariant = textInput,

        tertiaryFixed = disabledBackground,
        tertiaryFixedDim = borderDefault,
        onTertiaryFixed = textLabel,
        onTertiaryFixedVariant = textInput
    )

private fun SoptTypography.toMaterialTypography(): Typography =
    Typography(
        displayLarge = b28,
        displayMedium = b28,
        displaySmall = b28,
        headlineLarge = b28,
        headlineMedium = b28,
        headlineSmall = b28,
        titleLarge = b28,
        titleMedium = m18,
        titleSmall = sb16,
        bodyLarge = m18,
        bodyMedium = m14,
        bodySmall = m14,
        labelLarge = sb14,
        labelMedium = sb14,
        labelSmall = sb14
    )