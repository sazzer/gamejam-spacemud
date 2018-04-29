package uk.co.grahamcox.space.generation.galaxy

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import uk.co.grahamcox.space.generation.empire.EmpireSeedGenerator
import uk.co.grahamcox.space.generation.empire.EmpireSizeGenerator
import uk.co.grahamcox.space.generation.empire.EmpiresGenerator
import uk.co.grahamcox.space.generation.empire.render
import uk.co.grahamcox.space.generation.species.SpeciesGenerator
import uk.co.grahamcox.space.generation.starmap.ChiSquaredRadialStarMapGeneratorImpl
import uk.co.grahamcox.space.generation.starmap.render
import uk.co.grahamcox.space.generation.markov.SimpleMarkovChainGeneratorImpl

@Disabled
internal class GalaxyGeneratorIT {
    private val nameGenerator = SimpleMarkovChainGeneratorImpl(
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

    private val starMapGenerator = ChiSquaredRadialStarMapGeneratorImpl()

    private val speciesGenerator = SpeciesGenerator(nameGenerator)

    private val empiresGenerator = EmpiresGenerator(
            EmpireSizeGenerator(),
            EmpireSeedGenerator()
    )

    @Test
    fun test() {
        val testSubject = GalaxyGenerator(
                starMapGenerator,
                speciesGenerator,
                empiresGenerator
        )

        val galaxy = testSubject.generate("MyGalaxy", 100, 1000000, 10, 0.1)

        galaxy.starMap.render("/tmp/galaxy.png")
        galaxy.species.forEach { species ->
            galaxy.getEmpire(species)?.render("/tmp/empire-${species.name}.png")
        }
    }
}
