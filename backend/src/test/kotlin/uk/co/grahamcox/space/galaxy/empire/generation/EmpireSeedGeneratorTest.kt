package uk.co.grahamcox.space.galaxy.empire.generation

import org.apache.commons.math3.random.Well19937c
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import uk.co.grahamcox.space.galaxy.Coords
import uk.co.grahamcox.space.galaxy.empire.Empire
import uk.co.grahamcox.space.galaxy.starmap.StarMap

internal class EmpireSeedGeneratorTest {
    @Test
    fun test() {
        val starMap = StarMap(
                arrayOf(
                        intArrayOf(0, 1, 2, 3)
                )
        )

        val empire1 = Empire(
                arrayOf(
                        intArrayOf(0, 0, 0, 1)
                )
        )

        val empire2 = Empire(
                arrayOf(
                        intArrayOf(0, 0, 1, 0)
                )
        )

        val testSubject = EmpireSeedGenerator()
        val seed = testSubject.generateEmpireSeed(Well19937c(), starMap, listOf(empire1, empire2))

        Assertions.assertEquals(Coords(0, 1), seed)
    }
}
