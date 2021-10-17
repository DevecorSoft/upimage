package cn.devecor.upimage.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class MarkdownImgTest {

    @Test
    fun `should get an image markdown link`() {
        val url = "url"
        val name = "avatar.jpg"
        assertThat(MarkdownImg(name, url).link).isEqualTo(
            "![$name]($url)"
        )
    }
}
