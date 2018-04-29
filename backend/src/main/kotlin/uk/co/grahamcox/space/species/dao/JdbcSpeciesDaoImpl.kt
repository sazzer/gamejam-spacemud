package uk.co.grahamcox.space.species.dao

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import uk.co.grahamcox.space.dao.ResourceNotFoundException
import uk.co.grahamcox.space.galaxy.GalaxyId
import uk.co.grahamcox.space.species.SpeciesData
import uk.co.grahamcox.space.species.SpeciesId
import uk.co.grahamcox.space.model.Identity
import uk.co.grahamcox.space.model.Page
import uk.co.grahamcox.space.model.Resource
import uk.co.grahamcox.space.species.SpeciesTraits
import java.sql.ResultSet
import java.time.Clock
import java.util.*

/**
 * Implementation of the Species DAO in terms of a JDBC Data Source
 */
class JdbcSpeciesDaoImpl(
        private val clock: Clock,
        private val jdbcTemplate: NamedParameterJdbcTemplate,
        private val objectMapper: ObjectMapper
) : SpeciesDao {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(JdbcSpeciesDaoImpl::class.java)
    }

    /**
     * Create a new Species
     * @param data The data about the species to create
     * @return the created species
     */
    override fun create(data: SpeciesData): Resource<SpeciesId, SpeciesData> {
        LOG.debug("Creating a new species: {}", data)

        val newId = UUID.randomUUID().toString()
        val version = UUID.randomUUID().toString()
        val now = clock.instant()

        val encodedTraits = objectMapper.writeValueAsString(data.traits)
        try {
            jdbcTemplate.update("INSERT INTO species(species_id, version, created, updated, galaxy_id, name, traits) " +
                    "VALUES (:id::uuid, :version::uuid, :now, :now, :galaxy::uuid, :name, :traits::jsonb)",
                    mapOf(
                            "id" to newId,
                            "version" to version,
                            "now" to Date.from(now),
                            "galaxy" to data.galaxyId.id,
                            "name" to data.name,
                            "traits" to encodedTraits
                    ))

            return Resource(
                    identity = Identity(
                            id = SpeciesId(newId),
                            version = version,
                            created = now,
                            updated = now
                    ),
                    data = data
            )
        } catch (e: DuplicateKeyException) {
            LOG.warn("Duplicate key exception creating new species", e)
            throw DuplicateNameException()
        }
    }

    /**
     * Get a single Species by ID
     * @param id The ID of the species
     * @return the species
     */
    override fun getById(id: SpeciesId): Resource<SpeciesId, SpeciesData> {
        LOG.debug("Loading markov chain with ID: {}", id)

        try {
            return jdbcTemplate.queryForObject("SELECT * FROM species WHERE species_id = :id::uuid",
                    mapOf(
                            "id" to id.id
                    )) { rs, _ -> parseRecord(rs) }
        } catch (e: EmptyResultDataAccessException) {
            LOG.warn("No markov chain found with ID {}", id)
            throw ResourceNotFoundException(id)
        }
    }

    /**
     * List all of the species
     * @return the list of species
     */
    override fun list(): Page<SpeciesId, SpeciesData> {
        LOG.debug("Loading all species")

        val sql = StringBuilder()
        sql.append("SELECT * FROM species")
        sql.append(" ORDER BY NAME")
        val chains = jdbcTemplate.query(sql.toString()) { rs, _ -> parseRecord(rs) }
        return Page(chains, chains.size)
    }


    /**
     * Parse the record that the current row in this ResultSet points to
     *
     */
    private fun parseRecord(rs: ResultSet): Resource<SpeciesId, SpeciesData> {
        val identity = Identity(
                id = SpeciesId(rs.getString("species_id")),
                version = rs.getString("version"),
                created = rs.getTimestamp("created").toInstant(),
                updated = rs.getTimestamp("updated").toInstant()
        )

        val encodedTraits = rs.getString("traits")
        val parsedTraits = (objectMapper.readValue(encodedTraits, Map::class.java) as Map<String, Int>)
                .mapKeys { SpeciesTraits.valueOf(it.key) }

        val data = SpeciesData(
                galaxyId = GalaxyId(rs.getString("galaxy_id")),
                name = rs.getString("name"),
                traits = parsedTraits
        )

        return Resource(identity, data)
    }
}
