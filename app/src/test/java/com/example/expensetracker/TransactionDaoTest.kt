import androidx.room.Room
import com.example.expensetracker.data.dao.TransactionDao
import com.example.expensetracker.data.dao.CategoryDao
import com.example.expensetracker.data.database.AppDatabase
import com.example.expensetracker.data.entity.Transaction
import com.example.expensetracker.data.entity.Category
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)

class TransactionDaoTest {

    private lateinit var dao: TransactionDao
    private lateinit var categoryDao: CategoryDao
    private lateinit var database: AppDatabase

    private val testDispatcher = StandardTestDispatcher() // Use StandardTestDispatcher

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        // Initialize the Room database (use in-memory for testing)
        database = Room.inMemoryDatabaseBuilder(
            androidx.test.core.app.ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries() // Allow main thread queries for testing
            .build()

        dao = database.transactionDao()
        categoryDao = database.categoryDao()
    }

    @Test
    fun testInsertAndCalculateTotalIncomeAndExpenses() = runTest {

        val category = Category(name = "Food")
        categoryDao.insert(category)


        val categoryId = categoryDao.getAllCategories().first().id


        val incomeTransaction = Transaction(
            amount = 500.0,
            date = "2024-12-01",
            entryType = "Income",
            categoryId = categoryId
        )
        val expenseTransaction = Transaction(
            amount = 200.0,
            date = "2024-12-01",
            entryType = "Expense",
            categoryId = categoryId
        )

        dao.insert(incomeTransaction)
        dao.insert(expenseTransaction)


        val totalIncome = dao.getTotalIncome() // Should return Float
        val totalExpenses = dao.getTotalExpenses() // Should return Float


        assert(totalIncome.toDouble() == 500.0) // Convert totalIncome (Float) to Double for comparison
        assert(totalExpenses.toDouble() == 200.0) // Convert totalExpenses (Float) to Double for comparison
    }
}
