package io.vlk.livesporttask.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponsePlayer(
    val name: String,
    val sport: Value,
    val defaultCountry: Value,
    val images: List<Image> = listOf(),
    //not relevant part for task
    val id: String,
    val url: String,
    val gender: Value,
    val type: Value,
    val participantTypes: List<Value>,
    val favouriteKey: FavouriteKey? = null,
    val teams: List<Any>? = listOf(),
    val flagId: String? = null,
    val defaultTournament: Any? = null,
    val superTemplate: Any? = null,
)

@JsonClass(generateAdapter = true)
data class FavouriteKey(
    val web: String?,
    val portable: String?,
)

@JsonClass(generateAdapter = true)
data class Value(
    val id: Long,
    val name: String,
)

@JsonClass(generateAdapter = true)
data class Image(
    val path: String,
    val usageId: Long,
    val variantTypeId: Long,
)

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val code: Int,
    val message: String,
    val name: String,
    val stack: String? = null,
    val data: ErrorData? = null,
    val errors: List<ErrorItem> = listOf(),
)

@JsonClass(generateAdapter = true)
data class ErrorItem(val message: String, val type: String)

@JsonClass(generateAdapter = true)
data class ErrorData(val name: String)