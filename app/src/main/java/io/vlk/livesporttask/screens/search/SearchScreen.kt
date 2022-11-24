package io.vlk.livesporttask.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import io.vlk.livesporttask.R
import io.vlk.livesporttask.screens.search.model.FilterItem
import io.vlk.livesporttask.ui.component.FilterChipGroup
import io.vlk.livesporttask.ui.theme.PADDING_NORMAL
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.title)) })
        }
    ) { padding ->
        Surface(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SearchScreenContent()
        }
    }
}

@Composable
private fun SearchScreenContent() {
    Column(modifier = Modifier.padding(horizontal = PADDING_NORMAL)) {
        HeaderContent(
            changeSelection = {},
            onSearchChanged = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HeaderContent(
    changeSelection: (FilterItem) -> Unit,
    onSearchChanged: (String) -> Unit,
) {
    val textState = remember { mutableStateOf(TextFieldValue()) }

    Column {
        Row {
            TextField(
                modifier = Modifier.weight(1f),
                value = textState.value,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search,
                ),
                keyboardActions = KeyboardActions(onSearch = { onSearchChanged(textState.value.text) }),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            textState.value = TextFieldValue()
                            onSearchChanged(textState.value.text)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = stringResource(id = R.string.search_clear_description),
                        )
                    }
                },
                onValueChange = { textState.value = it }
            )
            Spacer(modifier = Modifier.width(PADDING_NORMAL))
            Button(onClick = { onSearchChanged(textState.value.text) }) {
                Text(
                    text = stringResource(id = R.string.search),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
        FilterChipGroup(
            modifier = Modifier.padding(vertical = PADDING_NORMAL),
            selectedItems = persistentListOf(),
            onSelectedChanged = changeSelection,
        )
    }
}