package uk.co.grahamcox.space.spring.database

import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.JdbcUtils
import org.springframework.transaction.annotation.Transactional
import javax.sql.DataSource

/**
 * Mechanism by which we can clean the database of all data
 * DO NOT USE IN A REAL SITUATION
 * @property dataSource the data source to clean
 * @property ignore The list of tables to ignore
 */
open class DatabaseCleaner(
        private val dataSource: DataSource,
        private val ignore: List<String>
) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(DatabaseCleaner::class.java)
    }

    /** The JDBC Template to use */
    private val jdbcTemplate = NamedParameterJdbcTemplate(dataSource)

    /**
     * Clean all of the tables in the database
     */
    @Transactional
    open fun clean() {
        val tables = listTables()
        if (tables.isNotEmpty()) {
            val sql = tables.joinToString(separator = ", ", prefix = "TRUNCATE ")
            jdbcTemplate.update(sql, mapOf<String, Any>())
        }

        LOG.info("Truncated tables: {}", tables)
    }

    /**
     * List all of the tables in the database to be cleaned
     * @return the tables
     */
    private fun listTables() : List<String> {
        val result = mutableListOf<String>()

        JdbcUtils.extractDatabaseMetaData(dataSource) { metadata ->
            val tables = metadata.getTables(metadata.userName, null, null, arrayOf("TABLE"))
            while (tables.next()) {
                val table = tables.getString(3)
                LOG.info("Considering table to truncate: {}", table)
                if (!ignore.contains(table.toLowerCase())) {
                    result.add(table)
                }
            }
        }

        return result
    }
}
