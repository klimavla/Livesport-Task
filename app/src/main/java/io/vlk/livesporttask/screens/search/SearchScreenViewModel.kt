package io.vlk.livesporttask.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.vlk.livesporttask.network.DataRepository
import io.vlk.livesporttask.network.ResponseState
import io.vlk.livesporttask.network.model.ResponsePlayer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {
    private val _screenDataState: MutableStateFlow<List<ResponsePlayer>> = MutableStateFlow(listOf())
    val screenDataState: Flow<List<ResponsePlayer>> = _screenDataState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.searchData(searchText = "Djo")
            repository.playerState.collect { playerList ->
                when (playerList) {
                    is ResponseState.Loading -> null
                    is ResponseState.Error -> null
                    is ResponseState.Success ->  playerList.value
                }?.apply { _screenDataState.emit(this) }
            }
        }
    }
}