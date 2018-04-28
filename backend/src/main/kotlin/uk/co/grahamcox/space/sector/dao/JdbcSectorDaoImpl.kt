package uk.co.grahamcox.space.sector.dao

import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import uk.co.grahamcox.space.dao.ResourceNotFoundException
import uk.co.grahamcox.space.galaxy.GalaxyId
import uk.co.grahamcox.space.sector.SectorData
import uk.co.grahamcox.space.sector.SectorId
import uk.co.grahamcox.space.model.Identity
import uk.co.grahamcox.space.model.Page
import uk.co.grahamcox.space.model.Resource
import java.sql.ResultSet
import java.time.Clock
import java.util.*

/**
 * Implementation of the Sector DAO in terms of a JDBC Data Source
 */
class JdbcSectorDaoImpl(
        private val clock: Clock,
        private val jdbcTemplate: NamedParameterJdbcTemplate
) : SectorDao {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(JdbcSectorDaoImpl::class.java)
    }

    /**
     * Create a new Sector
     * @param data The data about the sector to create
     * @return the created sector
     */
    override fun create(data: SectorData): Resource<SectorId, SectorData> {
        LOG.debug("Creating a new sector: {}", data)

        val newId = UUID.randomUUID().toString()
        val version = UUID.randomUUID().toString()
        val now = clock.instant()

        try {
            jdbcTemplate.update("INSERT INTO sectors(sector_id, version, created, updated, galaxy_id, x, y, stars) " +
                    "VALUES (:id::uuid, :version::uuid, :now, :now, :galaxy::uuid, :x, :y, :stars)",
                    mapOf(
                            "id" to newId,
                            "version" to version,
                            "now" to Date.from(now),
                            "galaxy" to data.galaxy.id,
                            "x" to data.x,
                            "y" to data.y,
                            "stars" to data.stars
                    ))

            return Resource(
                    identity = Identity(
                            id = SectorId(newId),
                            version = version,
                            created = now,
                            updated = now
                    ),
                    data = data
            )
        } catch (e: DuplicateKeyException) {
            LOG.warn("Duplicate key exception creating new sector", e)
            throw DuplicateCoordsException()
        }
    }

    /**
     * Get a single Sector by ID
     * @param id The ID of the sector
     * @return the sector
     */
    override fun getById(id: SectorId): Resource<SectorId, SectorData> {
        LOG.debug("Loading markov chain with ID: {}", id)

        try {
            return jdbcTemplate.queryForObject("SELECT * FROM sectors WHERE sector_id = :id::uuid",
                    mapOf(
                            "id" to id.id
                    )) { rs, _ -> parseRecord(rs) }
        } catch (e: EmptyResultDataAccessException) {
            LOG.warn("No markov chain found with ID {}", id)
            throw ResourceNotFoundException(id)
        }
    }

    /**
     * List all of the sectors
     * @return the list of sectors
     */
    override fun list(): Page<SectorId, SectorData> {
        LOG.debug("Loading all sectors")

        val sql = StringBuilder()
        sql.append("SELECT * FROM sectors")
        sql.append(" ORDER BY x, y")
        val chains = jdbcTemplate.query(sql.toString()) { rs, _ -> parseRecord(rs) }
        return Page(chains, chains.size)
    }


    /**
     * Parse the record that the current row in this ResultSet points to
     *
     */
    private fun parseRecord(rs: ResultSet): Resource<SectorId, SectorData> {
        val identity = Identity(
                id = SectorId(rs.getString("sector_id")),
                version = rs.getString("version"),
                created = rs.getTimestamp("created").toInstant(),
                updated = rs.getTimestamp("updated").toInstant()
        )

        val data = SectorData(
                galaxy = GalaxyId(rs.getString("galaxy_id")),
                x = rs.getInt("x"),
                y = rs.getInt("y"),
                stars = rs.getInt("stars")
        )

        return Resource(identity, data)
    }
}
