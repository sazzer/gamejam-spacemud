package uk.co.grahamcox.space.markov

import org.apache.commons.lang3.StringUtils
import org.apache.commons.math3.random.Well19937a
import org.junit.jupiter.api.Test

/**
 * Some tests for the Markov Chain Generator
 */
internal class MarkovChainGeneratorTest {
    /** The test subject */
    private val testSubject = MarkovChainGenerator(
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
                    2,
                    Well19937a()
            )

    /**
     * Test generating a single name
     */
    @Test
    fun testGenerateOne() {
        testSubject.generate()
    }

    /**
     * Test generating many names
     */
    @Test
    fun testGenerateMany() {
        val list = testSubject.generate(15)

        list
                .map { it.toLowerCase() }
                .map { StringUtils.capitalize(it) }
                .onEach(::println)
    }
}
