package uk.co.grahamcox.space.empire.dao

import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import uk.co.grahamcox.space.dao.ResourceNotFoundException
import uk.co.grahamcox.space.empire.EmpireData
import uk.co.grahamcox.space.empire.EmpireId
import uk.co.grahamcox.space.model.Identity
import uk.co.grahamcox.space.model.Page
import uk.co.grahamcox.space.model.Resource
import uk.co.grahamcox.space.sector.SectorId
import uk.co.grahamcox.space.species.SpeciesId
import java.sql.ResultSet
import java.time.Clock
import java.util.*

/**
 * Implementation of the Empire DAO in terms of a JDBC Data Source
 */
class JdbcEmpireDaoImpl(
        private val clock: Clock,
        private val jdbcTemplate: NamedParameterJdbcTemplate
) : EmpireDao {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(JdbcEmpireDaoImpl::class.java)
    }

    /**
     * Create a new Empire
     * @param data The data about the empire to create
     * @return the created empire
     */
    override fun create(data: EmpireData): Resource<EmpireId, EmpireData> {
        LOG.debug("Creating a new empire: {}", data)

        val newId = UUID.randomUUID().toString()
        val version = UUID.randomUUID().toString()
        val now = clock.instant()

        try {
            jdbcTemplate.update("INSERT INTO SpeciesSectors(species_sector_id, version, created, updated, species_id, sector_id, stars) " +
                    "VALUES (:id::uuid, :version::uuid, :now, :now, :species_id::uuid, :sector_id::uuid, :stars)",
                    mapOf(
                            "id" to newId,
                            "version" to version,
                            "now" to Date.from(now),
                            "species_id" to data.species.id,
                            "sector_id" to data.sector.id,
                            "stars" to data.stars
                    ))

            return Resource(
                    identity = Identity(
                            id = EmpireId(newId),
                            version = version,
                            created = now,
                            updated = now
                    ),
                    data = data
            )
        } catch (e: DuplicateKeyException) {
            LOG.warn("Duplicate key exception creating new empire", e)
            throw DuplicateSectorException()
        }
    }

    /**
     * Get a single Empire by ID
     * @param id The ID of the empire
     * @return the empire
     */
    override fun getById(id: EmpireId): Resource<EmpireId, EmpireData> {
        LOG.debug("Loading markov chain with ID: {}", id)

        try {
            return jdbcTemplate.queryForObject("SELECT * FROM SpeciesSectors WHERE species_sector_id = :id::uuid",
                    mapOf(
                            "id" to id.id
                    )) { rs, _ -> parseRecord(rs) }
        } catch (e: EmptyResultDataAccessException) {
            LOG.warn("No markov chain found with ID {}", id)
            throw ResourceNotFoundException(id)
        }
    }

    /**
     * List all of the empires
     * @return the list of empires
     */
    override fun list(): Page<EmpireId, EmpireData> {
        LOG.debug("Loading all empires")

        val sql = StringBuilder()
        sql.append("SELECT * FROM SpeciesSectors")
        sql.append(" ORDER BY NAME")
        val chains = jdbcTemplate.query(sql.toString()) { rs, _ -> parseRecord(rs) }
        return Page(chains, chains.size)
    }


    /**
     * Parse the record that the current row in this ResultSet points to
     *
     */
    private fun parseRecord(rs: ResultSet): Resource<EmpireId, EmpireData> {
        val identity = Identity(
                id = EmpireId(rs.getString("species_sector_id")),
                version = rs.getString("version"),
                created = rs.getTimestamp("created").toInstant(),
                updated = rs.getTimestamp("updated").toInstant()
        )

        val data = EmpireData(
                species = SpeciesId(rs.getString("species_id")),
                sector = SectorId(rs.getString("sector_id")),
                stars = rs.getInt("stars")
        )

        return Resource(identity, data)
    }
}
