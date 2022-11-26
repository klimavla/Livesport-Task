package io.vlk.livesporttask.screens.search

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.vlk.livesporttask.R
import io.vlk.livesporttask.screens.search.model.FilterItem
import io.vlk.livesporttask.screens.search.model.ListData
import io.vlk.livesporttask.screens.search.model.Player
import io.vlk.livesporttask.screens.search.model.SearchScreenDataState
import io.vlk.livesporttask.ui.component.FilterChipGroup
import io.vlk.livesporttask.ui.component.LoadingLayout
import io.vlk.livesporttask.ui.component.Section
import io.vlk.livesporttask.ui.theme.LiveSportTaskTheme
import io.vlk.livesporttask.ui.theme.PADDING_NORMAL
import io.vlk.livesporttask.ui.theme.PADDING_SMALL
import io.vlk.livesporttask.utils.MockHelper.mockList
import io.vlk.livesporttask.utils.afterSection
import io.vlk.livesporttask.utils.beforeSection
import io.vlk.livesporttask.utils.clipToRoundedShape
import io.vlk.livesporttask.utils.getItem
import io.vlk.livesporttask.utils.toListData
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel = hiltViewModel(),
) {
    val screenDataState by viewModel.screenDataState.collectAsState(SearchScreenDataState())

    val retryMessage = stringResource(id = R.string.action_retry)

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(screenDataState.errorMessage) {
        screenDataState.errorMessage?.let { message ->
            coroutineScope.launch {
                val snackbarResult = snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = retryMessage,
                )
                when (snackbarResult) {
                    SnackbarResult.Dismissed -> Unit
                    SnackbarResult.ActionPerformed -> viewModel.retrySearch()
                }
            }
        }
    }
    LoadingLayout(isLoading = screenDataState.isLoading) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
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
                SearchScreenContent(
                    screenDataState = screenDataState,
                    changeSelection = viewModel::changeSelection,
                    onSearchChanged = viewModel::onSearchChanged
                )
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchScreenContent(
    screenDataState: SearchScreenDataState,
    changeSelection: (FilterItem) -> Unit,
    onSearchChanged: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(modifier = Modifier.padding(horizontal = PADDING_NORMAL)) {
        HeaderPart(
            screenDataState = screenDataState,
            changeSelection = changeSelection,
            onSearchChanged = { text ->
                keyboardController?.hide()
                onSearchChanged(text)
            }
        )

        if (screenDataState.items.isEmpty()) {
            Text(
                text = stringResource(id = R.string.empty_data),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        } else {
            LazyColumn {
                itemsIndexed(screenDataState.items) { index, item ->
                    when (item) {
                        is ListData.Item -> PlayerItem(
                            player = item.data,
                            isFirst = item.data.beforeSection(screenDataState.getItem(index - 1)),
                            isLast = item.data.afterSection(screenDataState.getItem(index + 1)),
                        )
                        is ListData.Section -> Section(text = item.title)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HeaderPart(
    screenDataState: SearchScreenDataState,
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
            selectedItems = screenDataState.filterSelection.toImmutableList(),
            onSelectedChanged = changeSelection,
        )
    }
}

@Composable
private fun PlayerItem(player: Player, isFirst: Boolean, isLast: Boolean) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.clipToRoundedShape(isFirst, isLast),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = PADDING_SMALL, horizontal = PADDING_NORMAL),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(player.imageUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                error = painterResource(R.drawable.ic_launcher_foreground),
                fallback = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = stringResource(id = R.string.player_image_description),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .border(1.dp, MaterialTheme.colorScheme.secondary, CircleShape),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier) {
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = player.defaultCountry,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ScreenContentPreview() {
    LiveSportTaskTheme {
        SearchScreenContent(
            screenDataState = SearchScreenDataState(items = mockList.toListData()),
            changeSelection = {},
            onSearchChanged = {}
        )
    }
}