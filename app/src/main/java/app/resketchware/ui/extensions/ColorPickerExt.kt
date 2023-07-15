package app.resketchware.ui.extensions

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import kotlin.math.roundToInt

@ColorInt
fun Int.toSurfaceColor(): Int {
    if (ColorUtils.calculateLuminance(this) < 0.35) return Color.WHITE
    return Color.BLACK
}

fun Int.toHex() = String.format("#FF%06X", 0xFFFFFF and this)

fun Float.toSliderValue() = roundToInt().toString().padStart(3, '0')