package uk.co.grahamcox.space.galaxy.empire.generation

import uk.co.grahamcox.space.galaxy.Coords
import uk.co.grahamcox.space.galaxy.empire.Empire
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


/**
 * Render the given generated empire to the given file
 * @param galaxyFilename The file to render to
 */
fun Empire.render(galaxyFilename: String) {
    var max = 0

    for (x in 0 until this.width) {
        for (y in 0 until this.height) {
            val count = this.getSector(Coords(x, y))
            max = Math.max(max, count)
        }
    }

    System.out.println("Max: $max")

    val image = BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB)

    for (x in 0 until this.width) {
        for (y in 0 until this.height) {
            val count = this.getSector(Coords(x, y))
            val ratio = Math.min(255, Math.floor(1024 * count.toDouble() / max).toInt())

            val color = Color(ratio, ratio, ratio, 255)
            System.out.print(ratio.toString().padEnd(5))
            image.setRGB(x, y, color.rgb)
        }
        System.out.println()
    }

    ImageIO.write(image, "png", File(galaxyFilename))
}
