package cn.devecor.upimage.journey

import cn.devecor.upimage.Endpoints
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.core.io.Resource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import java.net.URL

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UpimageJourneyTest(
    @Autowired private val testRestTemplate: TestRestTemplate,
    @Autowired @Value("classpath:/image/image.jpg") private val image: Resource,
) {

    private val httpHeaders = HttpHeaders()
    private val linkedMultiValueMap = LinkedMultiValueMap<String, Any>()

    private lateinit var httpEntity: HttpEntity<LinkedMultiValueMap<String, Any>>

    @BeforeEach
    fun `set up`() {
        httpHeaders.clear()
        linkedMultiValueMap.clear()
        httpHeaders.contentType = MediaType.MULTIPART_FORM_DATA
        linkedMultiValueMap.add("file", image)
        httpEntity = HttpEntity<LinkedMultiValueMap<String, Any>>(linkedMultiValueMap, httpHeaders)
    }

    @Test
    fun `image upload and download`() {
        val postImageResponse = testRestTemplate.postForEntity<String>(Endpoints.IMAGE, httpEntity)

        assertThat(postImageResponse.statusCode).isEqualTo(HttpStatus.OK)

        val getImagePath = URL(postImageResponse.body!!).path

        val getImageResponse = testRestTemplate.getForEntity<Resource>(getImagePath)

        assertThat(getImageResponse.statusCode).isEqualTo(HttpStatus.OK)
    }
}