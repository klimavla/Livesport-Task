package io.vlk.livesporttask.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

//Based on https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-test/MIGRATION.md
@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineRule(startPaused: Boolean = false) : TestWatcher() {

    val testDispatcher = if (startPaused) StandardTestDispatcher() else UnconfinedTestDispatcher()

    override fun starting(description: Description) {
        super.starting(description)

        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()

        super.finished(description)
    }
}