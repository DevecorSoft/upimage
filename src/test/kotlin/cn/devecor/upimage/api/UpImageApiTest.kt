package cn.devecor.upimage.api

import cn.devecor.upimage.Endpoints
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
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
import java.io.File


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UpImageApiTest(
    @Autowired private val restTemplate: TestRestTemplate,
    @Autowired @Value("classpath:/asset/avatar.jpg") private val avatar: Resource,
    @Autowired @Value("\${upimage.home}") private val homePath: String
) {

    @Test
    fun `should get a link after image uploaded`() {

        val headers = HttpHeaders()
        headers.contentType = MediaType.MULTIPART_FORM_DATA
        val images = LinkedMultiValueMap<String, Any>()
        images.add("file", avatar)
        val httpEntity = HttpEntity<LinkedMultiValueMap<String, Any>>(images, headers)

        val link = restTemplate.postForEntity<String>(Endpoints.UPLOAD_IMAGE, httpEntity)

        assertThat(link.statusCode).isEqualTo(HttpStatus.OK)
    }

    @AfterEach
    internal fun tearDown() {
        File(homePath).deleteRecursively()
    }
}