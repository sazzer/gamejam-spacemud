package uk.co.grahamcox.space.galaxy.generation.rest

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * The input payload to represent the galaxy we are generating.
 * Note that the values here are requests, and the actual values will be based off of them but not exactly
 * @property name The name of the galaxy
 * @property requestedSize The requested size of the galaxy
 * @property requestedStars The requested number of stars in the galaxy
 * @property requestedSpecies The requested number of species in the galaxy
 * @property populationDensity The population density of the galaxy
 */
data class GalaxyGenerationInputModel(
        val name: String,
        @JsonProperty("requested_size") val requestedSize: Int,
        @JsonProperty("requested_number_of_stars") val requestedStars: Int,
        @JsonProperty("requested_number_of_species") val requestedSpecies: Int,
        @JsonProperty("population_density") val populationDensity: Double
)
