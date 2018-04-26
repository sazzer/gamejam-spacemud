package uk.co.grahamcox.space.galaxy.empire.generation

import org.apache.commons.math3.random.Well19937c
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import uk.co.grahamcox.space.galaxy.Coords
import uk.co.grahamcox.space.galaxy.empire.Empire
import uk.co.grahamcox.space.galaxy.species.Species
import uk.co.grahamcox.space.galaxy.starmap.StarMap
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

@Disabled
internal class EmpiresGeneratorTest {
    @Test
    fun test() {
        val starMap = Array(100) {
            IntArray(100) {
                10
            }
        }

        val speciesList = listOf(
                Species(name = "Species-1", traits = mapOf()),
                Species(name = "Species-2", traits = mapOf()),
                Species(name = "Species-3", traits = mapOf()),
                Species(name = "Species-4", traits = mapOf()),
                Species(name = "Species-5", traits = mapOf())
        )

        val testSubject = EmpiresGenerator(0.1,
                EmpireSizeGenerator(),
                EmpireSeedGenerator())

        val empires = testSubject.generate(Well19937c(), StarMap(starMap), speciesList)

        empires.forEach { species, empire ->
            System.out.println(species)
            empire.render("/tmp/empire-${species.name}.png")
        }
    }
}
