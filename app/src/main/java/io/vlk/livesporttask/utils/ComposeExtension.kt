package io.vlk.livesporttask.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import io.vlk.livesporttask.ui.theme.CORNER_RADIUS

fun Modifier.clipToRoundedShape(isFirst: Boolean, isLast: Boolean): Modifier = this
    .then(if (isFirst) Modifier.topRoundedClipShape() else Modifier)
    .then(if (isLast) Modifier.bottomRoundedClipShape() else Modifier)

private fun Modifier.topRoundedClipShape() = composed {
    clip(
        RoundedCornerShape(
            topStart = CORNER_RADIUS,
            topEnd = CORNER_RADIUS
        )
    )
}

private fun Modifier.bottomRoundedClipShape() = composed {
    clip(
        RoundedCornerShape(
            bottomStart = CORNER_RADIUS,
            bottomEnd = CORNER_RADIUS
        )
    )
}