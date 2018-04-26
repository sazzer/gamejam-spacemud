package uk.co.grahamcox.space.generation.markov

import org.apache.commons.lang3.StringUtils
import org.apache.commons.math3.random.Well19937a
import org.junit.jupiter.api.Test

/**
 * Some tests for the Markov Chain Generator
 */
internal class SimpleMarkovChainGeneratorImplTest {
    /** The test subject */
    private val testSubject = SimpleMarkovChainGeneratorImpl(
                    listOf(
                            "Aldebarans",
                            "Altairians",
                            "Amoeboid Zingatularians",
                            "Bartledannians",
                            "Belcerabons",
                            "Betelgeusians",
                            "Blagulon Kappans",
                            "Brontitallians",
                            "Dentrassis",
                            "Dolphins",
                            "G'Gugvuntts and Vl'hurgs",
                            "Golgafrinchans",
                            "Grebulons",
                            "Haggunenons",
                            "Hingefreel",
                            "Hooloovoo",
                            "Hrarf-Hrarfy",
                            "Humans",
                            "Jatravartids",
                            "Krikkiters",
                            "Lamuellans",
                            "Magratheans",
                            "Mice",
                            "Oglaroonians",
                            "Poghrils",
                            "Quarlvistians",
                            "Shaltanacs",
                            "Silastic Armourfiends of Striterax",
                            "Strangulous Stilettans of Jajazikstak",
                            "Strenuous Garfighters of Stug",
                            "Vogons"
                    ),
                    2
            )

    /**
     * Test generating a single name
     */
    @Test
    fun testGenerateOne() {
        testSubject.generate(Well19937a())
    }

    /**
     * Test generating many names
     */
    @Test
    fun testGenerateMany() {
        val list = testSubject.generate(Well19937a(), 15)

        list
                .map { it.toLowerCase() }
                .map { StringUtils.capitalize(it) }
                .onEach(::println)
    }
}
