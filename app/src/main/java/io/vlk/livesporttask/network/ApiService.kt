package io.vlk.livesporttask.network

import io.vlk.livesporttask.network.model.ResponsePlayer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/v2/search")
    suspend fun listPlayers(
        @Query("type-ids", encoded = true) types: String,
        @Query("q") search: String,
        @Query("project-type-id", encoded = true) projectTypeId: Int = 1,
        @Query("project-id", encoded = true) projectId: Int = 602,
        @Query("lang-id", encoded = true) langId: Int = 1,
        @Query("sport-ids", encoded = true) sportIds: String = "1,2,3,4,5,6,7,8,9",
    ): Response<List<ResponsePlayer>>
}