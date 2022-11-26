package io.vlk.livesporttask.utils

import io.vlk.livesporttask.network.model.FavouriteKey
import io.vlk.livesporttask.network.model.Image
import io.vlk.livesporttask.network.model.ResponsePlayer
import io.vlk.livesporttask.network.model.Value
import io.vlk.livesporttask.screens.search.model.Player

@Suppress("StringLiteralDuplication")
object MockHelper {
    val mockResponseList = listOf(
        ResponsePlayer(
            name = "Djokovic Novak",
            sport = Value(id = 2, name = "Tennis"),
            defaultCountry = Value(id = 167, name = "Serbia"),
            images = listOf(
                Image(
                    path = "tSfwGCdM-0rY6MEPI.png",
                    usageId = 3,
                    variantTypeId = 15,
                )
            ),
            id = "",
            url = "",
            gender = Value(id = 0, name = ""),
            type = Value(id = 0, name = ""),
            participantTypes = listOf(),
            favouriteKey = FavouriteKey(web = "", portable = ""),
            teams = listOf(),
            flagId = null,
            defaultTournament = null,
            superTemplate = null,

            ),
        ResponsePlayer(
            name = "Djurgarden",
            sport = Value(id = 1, name = "Soccer"),
            defaultCountry = Value(id = 181, name = "Sweden"),
            images = listOf(
                Image(
                    path = "WvY9pWzB-KzaQ78um.png",
                    usageId = 2,
                    variantTypeId = 15,
                )
            ),
            id = "",
            url = "",
            gender = Value(id = 0, name = ""),
            type = Value(id = 0, name = ""),
            participantTypes = listOf(),
            favouriteKey = FavouriteKey(web = "", portable = ""),
            teams = listOf(),
            flagId = null,
            defaultTournament = null,
            superTemplate = null
        ),
        ResponsePlayer(
            name = "Djibouti",
            sport = Value(id = 1, name = "Soccer"),
            defaultCountry = Value(id = 1, name = "Africa"),
            images = listOf(
                Image(
                    path = "rHfOPyyS-KMgMxU6t.png",
                    usageId = 2,
                    variantTypeId = 15,
                )
            ),
            id = "",
            url = "",
            gender = Value(id = 0, name = ""),
            type = Value(id = 0, name = ""),
            participantTypes = listOf(),
            favouriteKey = FavouriteKey(web = "", portable = ""),
            teams = listOf(),
            flagId = null,
            defaultTournament = null,
            superTemplate = null
        ),
        ResponsePlayer(
            name = "Djursholm U19",
            sport = Value(id = 1, name = "Soccer"),
            defaultCountry = Value(id = 181, name = "Sweden"),
            images = listOf(
                Image(
                    path = "nqE1HrAr-zoSBAnLp.png",
                    usageId = 2,
                    variantTypeId = 15,
                )
            ),
            id = "",
            url = "",
            gender = Value(id = 0, name = ""),
            type = Value(id = 0, name = ""),
            participantTypes = listOf(),
            favouriteKey = FavouriteKey(web = "", portable = ""),
            teams = listOf(),
            flagId = null,
            defaultTournament = null,
            superTemplate = null
        )
    )

    val mockList = listOf(
        Player(
            name = "Djokovic Novak",
            sport = "Tennis",
            defaultCountry = "Serbia",
            imageUrl = "tSfwGCdM-0rY6MEPI.png",
        ),
        Player(
            name = "Djurgarden",
            sport = "Soccer",
            defaultCountry = "Sweden",
            imageUrl = "WvY9pWzB-KzaQ78um.png"
        ),
        Player(
            name = "Djibouti",
            sport = "Soccer",
            defaultCountry = "Africa",
            imageUrl = "rHfOPyyS-KMgMxU6t.png",
        ),
        Player(
            name = "Djursholm U19",
            sport = "Soccer",
            defaultCountry = "Sweden",
            imageUrl = "nqE1HrAr-zoSBAnLp.png",
        )
    )
}