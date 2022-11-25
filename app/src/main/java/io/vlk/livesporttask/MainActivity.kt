package io.vlk.livesporttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import io.vlk.livesporttask.screens.search.SearchScreen
import io.vlk.livesporttask.ui.theme.LiveSportTaskTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LiveSportTaskTheme {
                SearchScreen()
            }
        }
    }
}