package io.vlk.livesporttask

import app.cash.turbine.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import io.vlk.livesporttask.network.DataRepository
import io.vlk.livesporttask.screens.search.SearchScreenViewModel
import io.vlk.livesporttask.screens.search.model.FilterItem
import io.vlk.livesporttask.screens.search.model.SearchScreenDataState
import io.vlk.livesporttask.utils.CoroutineRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FitBreakdownViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineRule = CoroutineRule()

    @MockK
    lateinit var repository: DataRepository

    private lateinit var viewModel: SearchScreenViewModel
    private val mockScreenData = SearchScreenDataState()

    @Before
    fun before() {
        MockKAnnotations.init(this)
        coEvery { repository.searchData(any(), any()) } just runs
        coEvery { repository.playerState } returns flowOf()
        viewModel = SearchScreenViewModel(repository = repository)
    }

    @Test
    fun initialData() = runTest {
        viewModel.screenDataState.test {
            assertEquals(SearchScreenDataState(), awaitItem())
        }
    }

    @Test
    fun testFilterChange() = runTest {
        var expectedFilterChange = listOf(FilterItem.TEAMS, FilterItem.SINGLE_PLAYER, FilterItem.TEAM_PLAYER)
        viewModel.changeSelection(FilterItem.CONTESTS)

        viewModel.screenDataState.test {
            testChange(expectedFilterChange, awaitItem())

            expectedFilterChange = listOf(FilterItem.SINGLE_PLAYER, FilterItem.TEAM_PLAYER)
            viewModel.changeSelection(FilterItem.TEAMS)
            testChange(expectedFilterChange, awaitItem())

            expectedFilterChange = listOf(FilterItem.TEAM_PLAYER)
            viewModel.changeSelection(FilterItem.SINGLE_PLAYER)
            testChange(expectedFilterChange, awaitItem())

            expectedFilterChange = listOf()
            viewModel.changeSelection(FilterItem.TEAM_PLAYER)
            testChange(expectedFilterChange, awaitItem())

            expectedFilterChange = listOf(
                FilterItem.ALL,
                FilterItem.CONTESTS,
                FilterItem.TEAMS,
                FilterItem.SINGLE_PLAYER,
                FilterItem.TEAM_PLAYER
            )
            viewModel.changeSelection(FilterItem.ALL)
            testChange(expectedFilterChange, awaitItem())

            expectedFilterChange = listOf(FilterItem.CONTESTS, FilterItem.SINGLE_PLAYER, FilterItem.TEAM_PLAYER)
            viewModel.changeSelection(FilterItem.TEAMS)
            testChange(expectedFilterChange, awaitItem())

            expectedFilterChange = listOf(
                FilterItem.ALL,
                FilterItem.CONTESTS,
                FilterItem.TEAMS,
                FilterItem.SINGLE_PLAYER,
                FilterItem.TEAM_PLAYER
            )
            viewModel.changeSelection(FilterItem.TEAMS)
            testChange(expectedFilterChange, awaitItem())
        }
    }

    private fun testChange(expectedFilterChange: List<FilterItem>, response: SearchScreenDataState) {
        assertEquals(mockScreenData.copy(filterSelection = expectedFilterChange), response)
        coVerify { repository.searchData(expectedFilterChange.map { it.typeId }.filter { it > 0 }, any()) }
    }

    @Test
    fun testSearch() = runTest {
        val expectedText = "Djob"

        viewModel.screenDataState.test {
            viewModel.onSearchChanged(expectedText)
            assertEquals(mockScreenData.copy(), awaitItem())
            assertEquals(mockScreenData.copy(searchText = expectedText, isLoading = false), awaitItem())
        }

        coVerify { repository.searchData(any(), searchText = expectedText) }
    }
}