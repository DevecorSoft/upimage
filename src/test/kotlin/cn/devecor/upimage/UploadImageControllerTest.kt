package cn.devecor.upimage

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.DisplayName

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.web.multipart.MultipartFile

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
                `when`(imageStorageService.getImage("")).thenReturn(null)

                val result = uploadImageController.getImage("xxx", "xxx.jpg")

                assertThat(result.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
                assertThat(result.body).isNull()
            }
        }
    }
}