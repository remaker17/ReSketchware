package app.resketchware.ui.core.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.resketchware.ui.core.model.Project
import app.resketchware.ui.theme.applyElevationOverlay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProjectEntry(
    modifier: Modifier = Modifier,
    project: Project,
    onEntryClick: () -> Unit = {}
) {
    val shape = RoundedCornerShape(12.dp)
    val background = MaterialTheme.colors.surface
        //.applyElevationOverlay(3.dp)

    // scale animation
    // val animatedProgress = remember {
        // Animatable(initialValue = 1.15f)
    // }
    // LaunchedEffect(Unit) {
        // animatedProgress.animateTo(
            // targetValue = 1f,
            // animationSpec = tween(300, easing = FastOutSlowInEasing)
        // )
    // }

    // val animatedModifier = modifier
        // .graphicsLayer(
            // scaleX = animatedProgress.value,
            // scaleY = animatedProgress.value
        // )

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(color = background)
            .clickable { onEntryClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = project.name,
                    style = MaterialTheme.typography.h6,
                    color = LocalContentColor.current,
                    maxLines = 2,
                    lineHeight = 20.sp
                )
                Text(
                    text = project.path,
                    fontSize = 12.sp,
                    lineHeight = 14.sp,
                    color = LocalContentColor.current.copy(alpha = 0.6f)
                )
            }
        }
    }
}
