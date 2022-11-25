package io.vlk.livesporttask.network

import com.squareup.moshi.Moshi
import io.vlk.livesporttask.network.model.ErrorResponse
import io.vlk.livesporttask.network.model.ResponsePlayer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import timber.log.Timber
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val service: ApiService,
) {
    private val playerStateFlow = MutableStateFlow<ResponseState<List<ResponsePlayer>>?>(null)
    val playerState: Flow<ResponseState<List<ResponsePlayer>>> = playerStateFlow.filterNotNull()

    suspend fun searchData(types: List<Int> = listOf(1, 2, 3, 4), searchText: String = "") {
        try {
            playerStateFlow.value = ResponseState.Loading()
            if (searchText.isEmpty()) {
                playerStateFlow.value = ResponseState.Success(listOf())
                return
            }
            val data = service.listPlayers(
                types = types.joinToString(","),
                search = searchText
            )

            if (data.isSuccessful) {
                playerStateFlow.value = ResponseState.Success(data.body() ?: listOf())
            } else {
                data.errorBody()?.let {
                    val eResponse = Moshi.Builder().build().adapter(ErrorResponse::class.java).fromJson(it.string())
                    playerStateFlow.value = ResponseState.Error(eResponse?.message ?: "")
                }
            }
        } catch (exception: Throwable) {
            val message = exception.message ?: ""
            Timber.e(message)
            playerStateFlow.value = ResponseState.Error(message)
        }
    }
}