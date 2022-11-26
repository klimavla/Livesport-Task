package io.vlk.livesporttask.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingLayout( isLoading: Boolean,modifier: Modifier = Modifier, content: @Composable () -> Unit = {}) {
    Box(modifier = modifier) {
        content()

        if (isLoading) {
            Surface(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                modifier = Modifier.fillMaxSize(),
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center).wrapContentSize(),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}