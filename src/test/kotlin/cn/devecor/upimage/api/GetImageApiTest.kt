package cn.devecor.upimage.api

import cn.devecor.upimage.Endpoints
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
import java.util.function.Supplier

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("get " + Endpoints.IMAGE)
class GetImageApiTest(
    @Autowired private val restTemplate: TestRestTemplate,
    @Autowired @Value("classpath:/asset/avatar.jpg") private val avatar: Resource,
    @Autowired private val upimageHomeSupplier: Supplier<String>
) {
    @Nested
    @DisplayName("when image is not existed")
    inner class NotExisted {
        @Test
        fun `should return 404`() {
            val result = restTemplate.getForEntity<Resource>("/get/image/not/exist.jpg")

            assertThat(result.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
        }
    }

    @Nested
    @DisplayName("when image is existed")
    inner class Existed {

        @Test
        fun `should return 200`() {
            avatar.file.copyTo(File("${upimageHomeSupplier.get()}/image/1634454401079/avatar.jpg"))

            val result = restTemplate.getForEntity<Resource>("/image/1634454401079/avatar.jpg")

            assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        }

        @BeforeEach
        @AfterEach
        fun cleanTestFolder() {
            File(upimageHomeSupplier.get()).deleteRecursively()
        }
    }
}