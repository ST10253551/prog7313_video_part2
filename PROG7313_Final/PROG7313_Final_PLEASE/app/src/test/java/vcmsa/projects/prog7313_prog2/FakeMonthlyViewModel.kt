import vcmsa.projects.prog7313_prog2.BudgetDAO

class FakeMonthlyViewModel(private val dao: BudgetDAO) {

    suspend fun calculateProgress(monthName: String, monthYear: String): Pair<Int, Int> {
        val goal = dao.getMonthlyGoal(monthName)
        val totalExpenses = dao.getTotalExpensesForMonth(monthYear) ?: 0.0

        return if (goal != null) {
            val maxProgress = (totalExpenses / goal.maxSpending * 100).toInt().coerceAtMost(100)
            val minProgress = (totalExpenses / goal.minGoal * 100).toInt().coerceAtMost(100)
            Pair(maxProgress, minProgress)
        } else {
            Pair(0, 0)
        }
    }
}
