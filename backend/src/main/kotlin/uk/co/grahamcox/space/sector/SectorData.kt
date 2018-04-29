package uk.co.grahamcox.space.sector

import uk.co.grahamcox.space.galaxy.GalaxyId

/**
 * The details of a single sector
 * @property galaxy The galaxy that the sector is in
 * @property x The X-ordinate of the sector
 * @property y The Y-ordinate of the sector
 * @property stars The number of stars in the sector
 */
data class SectorData(
        val galaxy: GalaxyId,
        val x: Int,
        val y: Int,
        val stars: Int
)
