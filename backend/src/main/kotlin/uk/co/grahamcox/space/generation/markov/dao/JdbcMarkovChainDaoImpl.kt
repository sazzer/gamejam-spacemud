package uk.co.grahamcox.space.generation.markov.dao

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import uk.co.grahamcox.space.dao.ResourceNotFoundException
import uk.co.grahamcox.space.generation.markov.MarkovChainData
import uk.co.grahamcox.space.generation.markov.MarkovChainId
import uk.co.grahamcox.space.model.Identity
import uk.co.grahamcox.space.model.Page
import uk.co.grahamcox.space.model.Resource
import java.sql.ResultSet
import java.time.Clock
import java.util.*

/**
 * JDBC Implementation of the Markov Chain DAO
 */
class JdbcMarkovChainDaoImpl(
        private val clock: Clock,
        private val jdbcTemplate: NamedParameterJdbcTemplate,
        private val objectMapper: ObjectMapper
) : MarkovChainDao {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(JdbcMarkovChainDaoImpl::class.java)
    }

    /**
     * Get a list of all the Markov Chains in the system
     * @param filters The filters to apply
     * @return the list of Markov Chains
     */
    override fun list(filters: MarkovChainFilters): Page<MarkovChainId, MarkovChainData> {
        LOG.debug("Loading all markov chains: {}", filters)

        val clauses = listOf(
                filters.type?.let { "type = :type" }
        ).filterNotNull()

        val sql = StringBuilder()
        sql.append("SELECT * FROM markovchains")
        if (clauses.isNotEmpty()) {
            sql.append(" WHERE ")
            sql.append(clauses.joinToString(separator = " AND "))
        }
        sql.append(" ORDER BY NAME")
        val chains = jdbcTemplate.query(sql.toString(), mapOf(
                "type" to filters.type
        )) { rs, _ -> parseRecord(rs) }
        return Page(chains, chains.size)
    }

    /**
     * Get a single Markov Chain by ID
     * @param id The Id of the chain
     * @return the chain
     */
    override fun getById(id: MarkovChainId): Resource<MarkovChainId, MarkovChainData> {
        LOG.debug("Loading markov chain with ID: {}", id)

        try {
            return jdbcTemplate.queryForObject("SELECT * FROM markovchains WHERE markov_chain_id = :id::uuid",
                    mapOf(
                            "id" to id.id
                    )) { rs, _ -> parseRecord(rs) }
        } catch (e: EmptyResultDataAccessException) {
            LOG.warn("No markov chain found with ID {}", id)
            throw ResourceNotFoundException(id)
        }
    }

    /**
     * Create a new Markov Chain
     * @param markovChain The data representing the new chain
     * @return the new chain
     */
    override fun create(markovChain: MarkovChainData): Resource<MarkovChainId, MarkovChainData> {
        LOG.debug("Creating a new markov chain: {}", markovChain)

        val newId = UUID.randomUUID().toString()
        val version = UUID.randomUUID().toString()
        val now = clock.instant()

        val jsonCorpus = objectMapper.writeValueAsString(markovChain.corpus)

        try {
            jdbcTemplate.update("INSERT INTO markovchains(markov_chain_id, version, created, updated, name, type, prefix, corpus) " +
                    "VALUES (:id::uuid, :version::uuid, :now, :now, :name, :type, :prefix, :corpus::jsonb)",
                    mapOf(
                            "id" to newId,
                            "version" to version,
                            "now" to Date.from(now),
                            "name" to markovChain.name,
                            "type" to markovChain.type,
                            "prefix" to markovChain.prefix,
                            "corpus" to jsonCorpus
                    ))

            return Resource(
                    identity = Identity(
                            id = MarkovChainId(newId),
                            version = version,
                            created = now,
                            updated = now
                    ),
                    data = markovChain
            )
        } catch (e: DuplicateKeyException) {
            LOG.warn("Duplicate key exception creating new markov chain", e)
            throw DuplicateNameException()
        }
    }

    /**
     * Update an existing Markov Chain
     * @param id The ID of the Markov Chain to update
     * @param markovChain The new data for the Markov Chain
     * @return the newly updated Markov Chain
     */
    override fun update(id: MarkovChainId, markovChain: MarkovChainData): Resource<MarkovChainId, MarkovChainData> {
        LOG.debug("Updating markov chain with ID {}: {}", id, markovChain)

        val version = UUID.randomUUID().toString()
        val now = clock.instant()

        val jsonCorpus = objectMapper.writeValueAsString(markovChain.corpus)

        try {
            jdbcTemplate.update("UPDATE markovchains SET version=:version::uuid, updated=:now, name=:name, type=:type, prefix=:prefix, corpus=:corpus::jsonb " +
                    "WHERE markov_chain_id=:id::uuid",
                    mapOf(
                            "id" to id.id,
                            "version" to version,
                            "now" to Date.from(now),
                            "name" to markovChain.name,
                            "type" to markovChain.type,
                            "prefix" to markovChain.prefix,
                            "corpus" to jsonCorpus
                    ))

            return getById(id)
        } catch (e: DuplicateKeyException) {
            LOG.warn("Duplicate key exception updating markov chain $id", e)
            throw DuplicateNameException()
        }
    }

    /**
     * Delete a Markov Chain
     * @param id The ID of the Markov Chain to delete
     */
    override fun delete(id: MarkovChainId) {
        LOG.debug("Deleting markov chain with ID: {}", id)
        val rowsDeleted = jdbcTemplate.update("DELETE FROM markovchains WHERE markov_chain_id = :id::uuid",
                mapOf("id" to id.id))

        LOG.debug("Deleted {} rows", rowsDeleted)
    }

    /**
     * Parse the record that the current row in this ResultSet points to
     *
     */
    private fun parseRecord(rs: ResultSet): Resource<MarkovChainId, MarkovChainData> {
        val identity = Identity(
                id = MarkovChainId(rs.getString("markov_chain_id")),
                version = rs.getString("version"),
                created = rs.getTimestamp("created").toInstant(),
                updated = rs.getTimestamp("updated").toInstant()
        )

        val encodedCorpus = rs.getString("corpus")
        val parsedCorpus = objectMapper.readValue(encodedCorpus, List::class.java) as List<String>

        val data = MarkovChainData(
                name = rs.getString("name"),
                type = rs.getString("type"),
                prefix = rs.getInt("prefix"),
                corpus = parsedCorpus
        )

        return Resource(identity, data)
    }
}
