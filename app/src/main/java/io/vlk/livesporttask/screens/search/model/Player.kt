package io.vlk.livesporttask.screens.search.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(
    val name: String,
    val sport: String,
    val defaultCountry: String,
    val imageUrl: String?,
) : Parcelable