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


    @Nested
    @DisplayName("handle images uploading")
    inner class HandleImageUpload {

        @InjectMocks
        private lateinit var uploadImageController: UploadImageController

        @Mock
        private lateinit var multipartFile: MultipartFile

        @Mock
        private lateinit var imageStorageService: ImageStorageService

        @Test
        fun `should get a markdown link for image`() {

            val link = "![avatar.jpg](avatar.jpg)"
            `when`(imageStorageService.handleImage(multipartFile)).thenReturn(link)

            val result = uploadImageController.handleImageUpload(multipartFile)

            assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(result.body).isEqualTo(link)
        }
    }
}