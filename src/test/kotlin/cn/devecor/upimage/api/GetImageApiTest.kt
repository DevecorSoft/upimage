package cn.devecor.upimage.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import java.io.File

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("get /image")
class GetImageApiTest(
    @Autowired private val restTemplate: TestRestTemplate,
    @Autowired @Value("classpath:/asset/avatar.jpg") private val avatar: Resource,
    @Autowired @Value("\${upimage.home}") private val homePath: String,
    @Autowired @Value("\${user.home}") private val userHome: String,
) {

    private val subpath = "1634454401079"
    private val filename = "avatar.jpg"

    @Nested
    @DisplayName("when image is not existed")
    inner class NotExisted {
        @Test
        fun `should return 404`() {
            val result = restTemplate.getForEntity<Resource>("/get/image/$subpath/$filename")

            assertThat(result.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
        }
    }

    @Nested
    @DisplayName("when image is existed")
    inner class Existed {
        private val testHome = "$userHome$homePath"
        private val imageFolder = "$testHome/$subpath"

        @BeforeEach
        fun setup() {
            File(imageFolder).mkdir()
            avatar.file.copyTo(File("$imageFolder/$filename"))
        }

        @Test
        fun `should return 200`() {
            val result = restTemplate.getForEntity<Resource>("/get/image/1634454401079/avatar.jpg")

            assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        }

        @BeforeEach
        @AfterEach
        fun cleanTestFolder() {
            File(testHome).deleteRecursively()
        }
    }
}