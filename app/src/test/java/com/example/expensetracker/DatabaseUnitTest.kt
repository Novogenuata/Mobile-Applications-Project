import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.expensetracker.data.dao.CategoryDao
import com.example.expensetracker.data.database.AppDatabase
import com.example.expensetracker.data.entity.Category
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runner.manipulation.Ordering

class CategoryDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var dao: CategoryDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.categoryDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertAndRetrieveCategory() = runBlocking {
        val category = Category(name = "Test Category")
        val id = dao.insert(category)
        val allCategories = dao.getAllCategories()
        val insertedCategory = allCategories.find { it.id == id }

        assertNotNull(insertedCategory)
        assertEquals("Test Category", insertedCategory?.name)
    }
}
