package io.vlk.livesporttask.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.vlk.livesporttask.ui.theme.PADDING_SMALL


@Composable
fun Section(text: String) {
    Text(
        modifier = Modifier
            .padding(top = PADDING_SMALL)
            .fillMaxWidth(),
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleSmall
    )
}