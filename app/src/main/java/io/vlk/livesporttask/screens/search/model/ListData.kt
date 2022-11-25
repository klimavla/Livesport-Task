package io.vlk.livesporttask.screens.search.model

sealed class ListData {
    data class Item(val data: Player) : ListData()
    data class Section(val title: String) : ListData()
}