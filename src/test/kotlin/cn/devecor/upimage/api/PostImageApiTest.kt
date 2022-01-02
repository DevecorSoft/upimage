package cn.devecor.upimage.api

import cn.devecor.upimage.Endpoints
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.core.io.Resource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import java.net.URL

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostImageApiTest(
    @Autowired private val testRestTemplate: TestRestTemplate
) {

    @Nested
    @DisplayName("given a path to image file")
    inner class GivenAPathToImageFile(
        @Autowired @Value("classpath:/image/image.jpg") private val image: Resource,
        @Autowired @Value("\${upimage.host}") private val host: String
    ) {

        @Nested
        @DisplayName("when post ${Endpoints.IMAGE}")
        inner class WhenPostImage {

            private val httpHeaders = HttpHeaders()
            private val linkedMultiValueMap = LinkedMultiValueMap<String, Any>()

            @Test
            fun `should get an image link`() {
                httpHeaders.contentType = MediaType.MULTIPART_FORM_DATA
                linkedMultiValueMap.add("file", image)
                val httpEntity = HttpEntity<LinkedMultiValueMap<String, Any>>(linkedMultiValueMap, httpHeaders)

                val response = testRestTemplate.postForEntity<String>(Endpoints.IMAGE, httpEntity)

                assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

                val actualUrl = URL(response.body!!).toString()
                val expectUrl = URL(host).toString()
                assertThat(actualUrl).startsWith(expectUrl)
            }
        }
    }
}