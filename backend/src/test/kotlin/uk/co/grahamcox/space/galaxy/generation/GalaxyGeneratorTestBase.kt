package uk.co.grahamcox.space.galaxy.generation

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * Base class for StarMap Generation tests
 */
open class GalaxyGeneratorTestBase {
    /**
     * Render the given generated starMap to the given file
     * @param starMap The starMap to render
     * @param galaxyFilename The file to render to
     */
    protected fun renderGalaxy(starMap: StarMap, galaxyFilename: String) {
        var max = 0

        for (x in 0 until starMap.width) {
            for (y in 0 until starMap.height) {
                val count = starMap.getSector(x, y)
                max = Math.max(max, count)
            }
        }

        System.out.println("Max: $max")

        val image = BufferedImage(starMap.width, starMap.height, BufferedImage.TYPE_INT_RGB)

        for (x in 0 until starMap.width) {
            for (y in 0 until starMap.height) {
                val count = starMap.getSector(x, y)
                val ratio = Math.min(255, Math.floor(1024 * count.toDouble() / max).toInt())

                val color = Color(ratio, ratio, ratio, 255)
                System.out.print(ratio.toString().padEnd(5))
                image.setRGB(x, y, color.rgb)
            }
            System.out.println()
        }

        ImageIO.write(image, "png", File(galaxyFilename))
    }
}
