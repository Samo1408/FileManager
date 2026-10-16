package app.filemg.samo1408.ui

import androidx.compose.runtime.staticCompositionLocalOf
import app.filemg.samo1408.core.data.FileManagerSettings
import app.filemg.samo1408.core.model.UiDensity

val LocalUiDensity = staticCompositionLocalOf { UiDensity.DEFAULT }

val LocalSettings = staticCompositionLocalOf { FileManagerSettings() }
