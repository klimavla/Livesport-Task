package io.vlk.livesporttask

import app.cash.turbine.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.vlk.livesporttask.network.ApiService
import io.vlk.livesporttask.network.DataRepository
import io.vlk.livesporttask.network.ResponseState
import io.vlk.livesporttask.screens.search.model.Player
import io.vlk.livesporttask.utils.CoroutineRule
import io.vlk.livesporttask.utils.MockHelper
import io.vlk.livesporttask.utils.model.LocaleConfiguration
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class DataRepositoryTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineRule = CoroutineRule()

    @MockK
    lateinit var apiService: ApiService

    private lateinit var dataRepository: DataRepository
    private val mockResponse = MockHelper.mockResponseList

    @Before
    fun before() {
        MockKAnnotations.init(this)

        dataRepository = DataRepository(apiService, config = LocaleConfiguration("", ""))
    }

    @Test
    fun testLoadData() = runTest {
        coEvery { apiService.listPlayers(any(), any()) } returns Response.success(mockResponse)

        dataRepository.searchData(listOf(), "a")
        dataRepository.playerState.test {
            assertEquals(ResponseState.Success(MockHelper.mockList), awaitItem())
        }

        coVerify { apiService.listPlayers(any(), any()) }
    }

    @Test
    fun testLoadFailedData() = runTest {
        coEvery { apiService.listPlayers(any(), any()) } returns Response.error(500, "".toResponseBody())

        dataRepository.searchData(listOf(), "a")
        dataRepository.playerState.test {
            assertTrue(awaitItem() is ResponseState.Error)
        }

        coVerify { apiService.listPlayers(any(), any()) }
    }


    @Test
    fun testLoadNoData() = runTest {
        coEvery { apiService.listPlayers(any(), any()) } returns Response.error(500, "".toResponseBody())

        dataRepository.searchData(listOf(), "")
        dataRepository.playerState.test {
            assertEquals(ResponseState.Success(listOf<Player>()), awaitItem())
        }

        coVerify(exactly = 0) { apiService.listPlayers(any(), any()) }
    }
}