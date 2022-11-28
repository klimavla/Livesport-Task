package io.vlk.livesporttask.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.vlk.livesporttask.network.DataRepository
import io.vlk.livesporttask.network.ResponseState
import io.vlk.livesporttask.screens.search.model.FilterItem
import io.vlk.livesporttask.screens.search.model.SearchScreenDataState
import io.vlk.livesporttask.utils.prepareData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {
    private val _screenDataState: MutableStateFlow<SearchScreenDataState> = MutableStateFlow(SearchScreenDataState())
    val screenDataState: Flow<SearchScreenDataState> = _screenDataState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.playerState.collect { playerList ->
                val screenData = _screenDataState.value
                when (playerList) {
                    is ResponseState.Loading -> screenData.copy(isLoading = true, errorMessage = null)
                    is ResponseState.Error -> screenData.copy(isLoading = false, errorMessage = playerList.message)
                    is ResponseState.Success -> screenData.copy(
                        isLoading = false,
                        errorMessage = null,
                        items = playerList.prepareData()
                    )
                }.apply { _screenDataState.emit(this) }
            }
        }
    }

    fun changeSelection(changedItem: FilterItem) = _screenDataState.value.apply {
        when {
            changedItem == FilterItem.ALL && filterSelection.contains(changedItem) -> listOf()
            changedItem != FilterItem.ALL && filterSelection.contains(changedItem) -> filterSelection - changedItem
            changedItem != FilterItem.ALL && !filterSelection.contains(changedItem) -> filterSelection + changedItem
            else -> filterItems
        }.let { list ->
            if (changedItem != FilterItem.ALL && !list.contains(FilterItem.ALL) && list.size == filterItems.size - 1) {
                filterItems
            } else if (list.size <= filterItems.size - 1) list - FilterItem.ALL
            else filterItems
        }.let { newSelectionList ->
            _screenDataState.value = this.copy(filterSelection = newSelectionList)
            viewModelScope.launch {
                repository.searchData(newSelectionList.filter { it.typeId > 0 }.map { it.typeId }, searchText)
            }
        }
    }

    fun onSearchChanged(text: String) = _screenDataState.value.apply {
        _screenDataState.value = this.copy(searchText = text)
        search(filterSelection, text)
    }

    fun retrySearch() = _screenDataState.value.apply { search(filterSelection, searchText) }

    private fun search(filterSelection: List<FilterItem>, text: String) = viewModelScope.launch {
        repository.searchData(filterSelection.filter { it.typeId > 0 }.map { it.typeId }, text)
    }
}