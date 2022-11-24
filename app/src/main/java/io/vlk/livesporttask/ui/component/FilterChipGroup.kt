package io.vlk.livesporttask.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.vlk.livesporttask.screens.search.model.FilterItem
import io.vlk.livesporttask.ui.theme.PADDING_MINI
import io.vlk.livesporttask.ui.theme.PADDING_SMALL
import io.vlk.livesporttask.ui.theme.SHADOW_ELEVATION
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Preview(showBackground = true)
@Composable
fun FilterChipGroup(
    modifier: Modifier = Modifier,
    filterItems: ImmutableList<FilterItem> = persistentListOf(*FilterItem.values()),
    selectedItems: ImmutableList<FilterItem> = persistentListOf(),
    onSelectedChanged: (FilterItem) -> Unit = {},
) {
    LazyRow(modifier = modifier) {
        items(filterItems) { item ->
            Chip(
                name = stringResource(id = item.resource),
                isSelected = selectedItems.contains(item),
                onSelectionChanged = {
                    onSelectedChanged(item)
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Chip(
    name: String = "Chip",
    isSelected: Boolean = false,
    onSelectionChanged: () -> Unit = {},
) {
    Surface(
        modifier = Modifier.padding(PADDING_MINI),
        shadowElevation = SHADOW_ELEVATION,
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = { onSelectionChanged() }
            )
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.labelSmall,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else Color.Black,
                modifier = Modifier.padding(PADDING_SMALL)
            )
        }
    }
}