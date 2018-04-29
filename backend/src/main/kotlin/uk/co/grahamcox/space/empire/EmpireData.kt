package uk.co.grahamcox.space.empire

import uk.co.grahamcox.space.sector.SectorId
import uk.co.grahamcox.space.species.SpeciesId

/**
 * The data about a sector in an empire
 * @property species The species that the empite belongs to
 * @property sector The sector in the empire
 * @property stars The number of stars in this sector owned by this species
 */
data class EmpireData(
        val species: SpeciesId,
        val sector: SectorId,
        val stars: Int
)
