package uk.co.grahamcox.space.spring.database

import org.springframework.core.Ordered
import org.springframework.test.context.TestContext
import org.springframework.test.context.support.AbstractTestExecutionListener

/**
 * Spring TestExecutionListener to clean the database before the test runs
 */
class DatabaseCleanerTestExecutionListener : AbstractTestExecutionListener() {

    /**
     * Clean the database before each test runs
     */
    override fun beforeTestMethod(testContext: TestContext) {
        val databaseCleaner = testContext.applicationContext.getBean(DatabaseCleaner::class.java)
        databaseCleaner.clean()
    }

    /**
     * The default implementation returns [Ordered.LOWEST_PRECEDENCE],
     * thereby ensuring that custom listeners are ordered after default
     * listeners supplied by the framework. Can be overridden by subclasses
     * as necessary.
     * @since 4.1
     */
    override fun getOrder(): Int {
        return Ordered.HIGHEST_PRECEDENCE
    }
}
