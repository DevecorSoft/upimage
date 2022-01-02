package cn.devecor.upimage.config

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ImageStorageConfigTest {

    private val imageStorageConfig = ImageStorageConfig()

    @Test
    fun `should return upimage home path`() {
        val result = imageStorageConfig.upimageHomeSupplier("/upimage-home").get()

        assertThat(result).isEqualTo("/upimage-home")
    }

    @Test
    fun `should return abs path when it starts with ~`() {
        val result = imageStorageConfig.upimageHomeSupplier("~/upimage").get()

        assertThat(result).isEqualTo("${ System.getenv("HOME") }/upimage")
    }
}