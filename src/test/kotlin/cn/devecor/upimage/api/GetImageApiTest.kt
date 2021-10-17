package cn.devecor.upimage.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetImageApiTest(
    @Autowired private val restTemplate: TestRestTemplate,

    ) {

    @Test
    fun `should download a image file`() {
        val result = restTemplate.getForEntity<Resource>("/get/image/1634454401079/avatar.jpg")

        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
    }
}