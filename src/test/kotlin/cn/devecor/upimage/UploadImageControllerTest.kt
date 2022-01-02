package cn.devecor.upimage

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.DisplayName

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.multipart.MultipartFile
import java.io.File

@ExtendWith(MockitoExtension::class)
internal class UploadImageControllerTest {
    @Mock
    private lateinit var imageStorageService: ImageStorageService

    @Mock
    private lateinit var multipartFile: MultipartFile

    @InjectMocks
    private lateinit var uploadImageController: UploadImageController

    @Nested
    @DisplayName("handle images uploading")
    inner class PostImage {
        @Test
        fun `should return a markdown link for image`() {

            val link = "![avatar.jpg](avatar.jpg)"
            `when`(imageStorageService.handleImage(multipartFile)).thenReturn(link)

            val result = uploadImageController.handleImageUpload(multipartFile)

            assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(result.body).isEqualTo(link)
        }
    }

    @Nested
    @DisplayName("handle get image")
    inner class GetImage {
        @Nested
        @DisplayName("when image is not existed")
        inner class NotExisted {
            @Test
            fun `should return 404 as http status and null as body`() {
                `when`(imageStorageService.getImage("/xxx/xxx.jpg")).thenReturn(null)

                val result = uploadImageController.getImage("xxx", "xxx.jpg")

                assertThat(result.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
                assertThat(result.body).isNull()
            }
        }

        @Nested
        @DisplayName("when image is existed")
        inner class Existed {
            @Test
            fun `should return 200 as http status and resource as body`() {
                `when`(imageStorageService.getImage("/xxx/xxx.jpg")).thenReturn(File("src/test/resources/asset/avatar.jpg"))

                val result = uploadImageController.getImage("xxx", "xxx.jpg")

                assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
                assertThat((result.body as Resource).exists()).isTrue
            }

            @Test
            fun `should return an instance of inputStreamResource as body`() {
                val file = File("src/test/resources/asset/avatar.jpg")
                `when`(imageStorageService.getImage("/xxx/xxx.jpg")).thenReturn(file)

                val result = uploadImageController.getImage("xxx", "xxx.jpg")

                assertThat(result.body is InputStreamResource).isTrue
                assertThat(result.headers.contentType).isEqualTo(MediaType.APPLICATION_OCTET_STREAM)
                assertThat(result.headers.contentLength).isEqualTo(file.length())
            }
        }
    }

    @Nested
    @DisplayName("given a multipart file as image")
    inner class GivenAMultipartFileAsImage {
        @Nested
        @DisplayName("when save image via controller")
        inner class WhenCallControllerWithThisMultipartFile {
            @Test
            fun `then should call image storage service`() {
                uploadImageController.postImage(multipartFile)

                verify(imageStorageService).saveImage(multipartFile)
            }

            @ParameterizedTest(name = "if service return url {0}")
            @CsvSource(
                "http://localhost/12345678/image.png",
                "https://host:port/path/imageid/name.jpg"
            )
            fun `then should return responseEntity with this url`(url: String) {
                `when`(imageStorageService.saveImage(multipartFile)).thenReturn(url)

                val responseEntity = uploadImageController.postImage(multipartFile)

                assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
                assertThat(responseEntity.body).isEqualTo(url)
            }
        }
    }
}