package uk.co.grahamcox.space.galaxy.generation

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * Base class for Galaxy Generation tests
 */
open class GalaxyGeneratorTestBase {
    /**
     * Render the given generated galaxy to the given file
     * @param galaxy The galaxy to render
     * @param galaxyFilename The file to render to
     */
    protected fun renderGalaxy(galaxy: Galaxy, galaxyFilename: String) {
        var max = 0

        for (x in 0 until galaxy.width) {
            for (y in 0 until galaxy.height) {
                val count = galaxy.getSector(x, y).starCount
                max = Math.max(max, count)
            }
        }

        System.out.println("Max: $max")

        val image = BufferedImage(galaxy.width, galaxy.height, BufferedImage.TYPE_INT_RGB)

        for (x in 0 until galaxy.width) {
            for (y in 0 until galaxy.height) {
                val count = galaxy.getSector(x, y).starCount
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
