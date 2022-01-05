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
import java.net.URL


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

        val responseEntity = restTemplate.postForEntity<String>(Endpoints.UPLOAD_IMAGE, httpEntity)

        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)

        val url = responseEntity.body!!.split("(").last()
        val urlPath = URL(url.substring(0, url.length - 1)).path
        val filePath = "${System.getenv("HOME")}/upimage/$urlPath"

        assertThat(urlPath).endsWith("avatar.jpg")
        assertThat(File(filePath)).exists()
        assertThat(File(filePath)).isFile
    }

    @AfterEach
    internal fun tearDown() {
        File(homePath).deleteRecursively()
    }
}