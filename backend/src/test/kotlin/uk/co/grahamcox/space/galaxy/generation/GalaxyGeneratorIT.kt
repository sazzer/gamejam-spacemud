package uk.co.grahamcox.space.galaxy.generation

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import uk.co.grahamcox.space.galaxy.empire.generation.EmpireSeedGenerator
import uk.co.grahamcox.space.galaxy.empire.generation.EmpireSizeGenerator
import uk.co.grahamcox.space.galaxy.empire.generation.EmpiresGenerator
import uk.co.grahamcox.space.galaxy.empire.generation.render
import uk.co.grahamcox.space.galaxy.species.generation.SpeciesGenerator
import uk.co.grahamcox.space.galaxy.starmap.generation.ChiSquaredRadialStarMapGeneratorImpl
import uk.co.grahamcox.space.galaxy.starmap.generation.render
import uk.co.grahamcox.space.generation.Rng
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
