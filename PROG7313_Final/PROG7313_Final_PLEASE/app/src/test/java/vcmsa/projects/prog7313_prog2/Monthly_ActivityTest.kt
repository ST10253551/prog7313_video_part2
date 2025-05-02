package vcmsa.projects.prog7313_prog2.ui

import FakeMonthlyViewModel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import vcmsa.projects.prog7313_prog2.BudgetDAO
import vcmsa.projects.prog7313_prog2.MonthlyGoal

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class Monthly_ActivityTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FakeMonthlyViewModel
    private lateinit var dao: BudgetDAO

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        dao = mock(BudgetDAO::class.java)
        viewModel = FakeMonthlyViewModel(dao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test progress calculations with valid monthly goal and expenses`() = runTest {
        // Given
        val monthName = "May"
        val monthYear = "2025-05"
        val goal = MonthlyGoal(month = monthName, maxSpending = 500.0, minGoal = 300.0)
        val totalExpenses = 150.0

        `when`(dao.getMonthlyGoal(monthName)).thenReturn(goal)
        `when`(dao.getTotalExpensesForMonth(monthYear)).thenReturn(totalExpenses)

        // When
        val (maxProgress, minProgress) = viewModel.calculateProgress(monthName, monthYear)

        // Then
        assertEquals(30, maxProgress)  // 150 / 500 * 100
        assertEquals(50, minProgress)  // 150 / 300 * 100
    }

    @Test
    fun `test progress does not exceed 100 percent`() = runTest {
        val monthName = "May"
        val monthYear = "2025-05"
        val goal = MonthlyGoal(month = monthName, maxSpending = 500.0, minGoal = 300.0)
        val totalExpenses = 1000.0

        `when`(dao.getMonthlyGoal(monthName)).thenReturn(goal)
        `when`(dao.getTotalExpensesForMonth(monthYear)).thenReturn(totalExpenses)

        val (maxProgress, minProgress) = viewModel.calculateProgress(monthName, monthYear)

        assertEquals(100, maxProgress)  // Should be capped
        assertEquals(100, minProgress)
    }
}
