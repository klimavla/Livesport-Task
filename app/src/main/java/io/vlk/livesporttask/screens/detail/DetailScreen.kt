package io.vlk.livesporttask.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.vlk.livesporttask.R
import io.vlk.livesporttask.screens.search.model.Player
import io.vlk.livesporttask.ui.theme.CORNER_RADIUS
import io.vlk.livesporttask.ui.theme.PADDING_NORMAL

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun DetailScreen(player: Player, navigator: DestinationsNavigator, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            MediumTopAppBar(
                navigationIcon = {
                    Icon(
                        modifier = modifier.clickable { navigator.navigateUp() },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_description),
                    )
                },
                title = { Text(text = player.name) },
            )
        }
    ) { padding ->
        Surface(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = PADDING_NORMAL)
                    .padding(bottom = PADDING_NORMAL),
            ) {
                Row() {
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
                            .size(100.dp)
                            .clip(RoundedCornerShape(CORNER_RADIUS))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(CORNER_RADIUS)),
                    )
                    Column(modifier = Modifier.padding(PADDING_NORMAL)) {
                        Text(
                            text = player.sport,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                        Text(
                            text = player.defaultCountry,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
            }
        }
    }
}