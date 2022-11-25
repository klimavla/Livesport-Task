package io.vlk.livesporttask.utils

import io.vlk.livesporttask.network.ResponseState
import io.vlk.livesporttask.network.model.ResponsePlayer
import io.vlk.livesporttask.utils.model.LocaleConfiguration
import io.vlk.livesporttask.screens.search.model.Player
import io.vlk.livesporttask.screens.search.model.ListData
import io.vlk.livesporttask.screens.search.model.SearchScreenDataState

internal fun List<ResponsePlayer>.toPlayerData(config: LocaleConfiguration): List<Player> = map { responsePlayer ->
    val imageUrl = responsePlayer.images.firstOrNull()?.path
    Player(
        name = responsePlayer.name,
        sport = responsePlayer.sport.name,
        defaultCountry = responsePlayer.defaultCountry.name,
        imageUrl = "${config.imageUrl}${imageUrl}".takeIf { !imageUrl.isNullOrBlank() }
    )
}

internal fun ResponseState.Success<List<Player>>.prepareData(): List<ListData> = value.toListData()

internal fun List<Player>.toListData(): List<ListData> = buildList {
    val sortedList = this@toListData.sortedWith(compareBy({ it.sport }, { it.name }))
    sortedList.forEachIndexed { index, item ->
        item.sectionBetween(sortedList.getOrNull(index - 1))?.let { add(it) }
        add(ListData.Item(item))
    }
}

internal fun SearchScreenDataState.getItem(position: Int): Player? {
    val item = items.getOrNull(position)
    return if (item is ListData.Item) item.data else null
}

internal fun Player.afterSection(before: Player?): Boolean = this.sport != before?.sport
internal fun Player.beforeSection(before: Player?): Boolean = before == null || this.sport != before.sport

internal fun Player.sectionBetween(before: Player?): ListData.Section? = when {
    before == null || this.sport != before.sport -> ListData.Section(title = this.sport)
    else -> null
}
