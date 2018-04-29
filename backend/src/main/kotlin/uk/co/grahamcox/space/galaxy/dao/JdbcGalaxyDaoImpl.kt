package uk.co.grahamcox.space.galaxy.dao

import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import uk.co.grahamcox.space.dao.ResourceNotFoundException
import uk.co.grahamcox.space.galaxy.GalaxyData
import uk.co.grahamcox.space.galaxy.GalaxyId
import uk.co.grahamcox.space.model.Identity
import uk.co.grahamcox.space.model.Page
import uk.co.grahamcox.space.model.Resource
import java.sql.ResultSet
import java.time.Clock
import java.util.*

/**
 * Implementation of the Galaxy DAO in terms of a JDBC Data Source
 */
class JdbcGalaxyDaoImpl(
        private val clock: Clock,
        private val jdbcTemplate: NamedParameterJdbcTemplate
) : GalaxyDao {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(JdbcGalaxyDaoImpl::class.java)
    }

    /**
     * Create a new Galaxy
     * @param data The data about the galaxy to create
     * @return the created galaxy
     */
    override fun create(data: GalaxyData): Resource<GalaxyId, GalaxyData> {
        LOG.debug("Creating a new galaxy: {}", data)

        val newId = UUID.randomUUID().toString()
        val version = UUID.randomUUID().toString()
        val now = clock.instant()

        try {
            jdbcTemplate.update("INSERT INTO galaxies(galaxy_id, version, created, updated, name, width, height) " +
                    "VALUES (:id::uuid, :version::uuid, :now, :now, :name, :width, :height)",
                    mapOf(
                            "id" to newId,
                            "version" to version,
                            "now" to Date.from(now),
                            "name" to data.name,
                            "width" to data.width,
                            "height" to data.height
                    ))

            return Resource(
                    identity = Identity(
                            id = GalaxyId(newId),
                            version = version,
                            created = now,
                            updated = now
                    ),
                    data = data
            )
        } catch (e: DuplicateKeyException) {
            LOG.warn("Duplicate key exception creating new galaxy", e)
            throw DuplicateNameException()
        }
    }

    /**
     * Get a single Galaxy by ID
     * @param id The ID of the galaxy
     * @return the galaxy
     */
    override fun getById(id: GalaxyId): Resource<GalaxyId, GalaxyData> {
        LOG.debug("Loading markov chain with ID: {}", id)

        try {
            return jdbcTemplate.queryForObject("SELECT * FROM galaxies WHERE galaxy_id = :id::uuid",
                    mapOf(
                            "id" to id.id
                    )) { rs, _ -> parseRecord(rs) }
        } catch (e: EmptyResultDataAccessException) {
            LOG.warn("No markov chain found with ID {}", id)
            throw ResourceNotFoundException(id)
        }
    }

    /**
     * List all of the galaxies
     * @return the list of galaxies
     */
    override fun list(): Page<GalaxyId, GalaxyData> {
        LOG.debug("Loading all galaxies")

        val sql = StringBuilder()
        sql.append("SELECT * FROM galaxies")
        sql.append(" ORDER BY NAME")
        val chains = jdbcTemplate.query(sql.toString()) { rs, _ -> parseRecord(rs) }
        return Page(chains, chains.size)
    }


    /**
     * Parse the record that the current row in this ResultSet points to
     *
     */
    private fun parseRecord(rs: ResultSet): Resource<GalaxyId, GalaxyData> {
        val identity = Identity(
                id = GalaxyId(rs.getString("galaxy_id")),
                version = rs.getString("version"),
                created = rs.getTimestamp("created").toInstant(),
                updated = rs.getTimestamp("updated").toInstant()
        )

        val data = GalaxyData(
                name = rs.getString("name"),
                width = rs.getInt("width"),
                height = rs.getInt("height")
        )

        return Resource(identity, data)
    }
}
