package vcmsa.projects.prog7313_prog2

import org.junit.Assert.*
import org.junit.Test
import java.nio.charset.Charset

class ExpenseEntityTest {

    @Test
    fun `test ExpenseEntity field assignment`() {
        val imageBytes = "sampleImage".toByteArray(Charset.defaultCharset())

        val entity = ExpenseEntity(
            id = 5,
            expenseTitle = "Lunch",
            expenseDescription = "Chicken wrap and soda",
            expenseCategory = "Food",
            expenseAmount = "65.50",
            expenseDateTime = "2025-05-01 13:15",
            expenseImage = imageBytes
        )

        assertEquals(5, entity.id)
        assertEquals("Lunch", entity.expenseTitle)
        assertEquals("Chicken wrap and soda", entity.expenseDescription)
        assertEquals("Food", entity.expenseCategory)
        assertEquals("65.50", entity.expenseAmount)
        assertEquals("2025-05-01 13:15", entity.expenseDateTime)
        assertArrayEquals(imageBytes, entity.expenseImage)
    }

    @Test
    fun `test ExpenseEntity handles null image`() {
        val entity = ExpenseEntity(
            id = 6,
            expenseTitle = "Taxi",
            expenseDescription = "Ride home",
            expenseCategory = "Transport",
            expenseAmount = "80.00",
            expenseDateTime = "2025-05-01 18:45",
            expenseImage = null
        )

        assertNull(entity.expenseImage)
    }

    @Test
    fun `test ExpenseEntity equality and copy`() {
        val original = ExpenseEntity(
            id = 10,
            expenseTitle = "Data Bundle",
            expenseDescription = "MTN 1GB",
            expenseCategory = "Utilities",
            expenseAmount = "35.00",
            expenseDateTime = "2025-05-01 07:00",
            expenseImage = null
        )

        val copy = original.copy()

        assertEquals(original, copy)
        assertEquals(original.hashCode(), copy.hashCode())
    }
}
