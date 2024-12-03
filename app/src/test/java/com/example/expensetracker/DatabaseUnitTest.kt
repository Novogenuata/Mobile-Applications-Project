import androidx.room.Room
import com.example.expensetracker.data.dao.CategoryDao
import com.example.expensetracker.data.database.AppDatabase
import com.example.expensetracker.data.entity.Category
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(RobolectricTestRunner::class)
class CategoryDaoTest {

    private lateinit var dao: CategoryDao
    private lateinit var database: AppDatabase // Corrected the typo here

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

        dao = database.categoryDao() // Use the database to get the DAO
    }

    @Test
    fun testInsertAndRetrieveCategory() = runTest {
        val category = Category(name = "Test Category")

        val id = dao.insert(category) // Inserting the category
        val allCategories = dao.getAllCategories()

        val insertedCategory = allCategories.find { it.id == id }
        assert(insertedCategory != null) // Ensure it was inserted
        assert(insertedCategory?.name == "Test Category") // Verify the name
    }
}
