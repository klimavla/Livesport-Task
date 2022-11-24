package io.vlk.livesporttask.screens.search.model

import androidx.annotation.StringRes
import io.vlk.livesporttask.R

enum class FilterItem(val typeId: Int, @StringRes val resource: Int) {
    ALL(0, R.string.filter_all),
    CONTESTS(1, R.string.filter_contest),
    TEAMS(2, R.string.filter_team),
    SINGLE_PLAYER(3, R.string.filter_player_single),
    TEAM_PLAYER(4, R.string.filter_player_team),
}