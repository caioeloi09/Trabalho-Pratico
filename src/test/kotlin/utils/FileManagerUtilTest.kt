package utils

import com.google.gson.Gson
import org.junit.jupiter.api.*
import java.io.File
import org.eng.soft.two.entity.Account
import org.eng.soft.two.utils.FileManagerUtil

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileManagerUtilTest {
    private lateinit var fileManagerUtil: FileManagerUtil
    private lateinit var testFilePath: String

    @BeforeEach
    fun setUp() {
        testFilePath = "test_accounts.json"
        fileManagerUtil = FileManagerUtil(testFilePath)
    }

    @AfterEach
    fun tearDown() {
        val testFile = File(testFilePath)
        if (testFile.exists()) {
            testFile.delete()
        }
    }

    @Test
    fun `saveAccounts should save accounts to file`() {
        val accounts = mapOf(
            1L to Account(
                1, 500.0,
                password = "password1"
            ),
            2L to Account(
                2, 500.0,
                password = "password2"
            )
        )

        fileManagerUtil.saveAccounts(accounts)

        val savedFile = File(testFilePath)
        Assertions.assertTrue(savedFile.exists())

        val content = savedFile.readText()
        Assertions.assertTrue(content.contains("password1"))
        Assertions.assertTrue(content.contains("password2"))
    }

    @Test
    fun `loadAccounts should correctly load accounts from file`() {
        val accounts = mapOf(
            1L to Account(
                1, 500.0,
                password = "password1"
            ),
            2L to Account(
                2, 500.0,
                password = "password2"
            )
        )

        File(testFilePath).writeText(Gson().toJson(accounts))

        val loadedAccounts = fileManagerUtil.loadAccounts()

        Assertions.assertEquals(2, loadedAccounts.size)
        Assertions.assertEquals("password1", loadedAccounts[1]?.password)
        Assertions.assertEquals("password2", loadedAccounts[2]?.password)
    }

    @Test
    fun `loadAccounts should return empty map if file does not exist`() {
        val loadedAccounts = fileManagerUtil.loadAccounts()

        Assertions.assertTrue(loadedAccounts.isEmpty())
    }
}
