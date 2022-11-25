package io.vlk.livesporttask.screens.search.model

data class SearchScreenDataState(
    val filterSelection: List<FilterItem> = FilterItem.values().asList(),
    val filterItems: List<FilterItem> = FilterItem.values().asList(),
    val items: List<ListData> = listOf(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchText: String = "",
)