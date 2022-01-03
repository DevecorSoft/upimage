package cn.devecor.upimage

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.MediaType

@ExtendWith(MockitoExtension::class)
internal class ImageMediaTypeServiceTest {
    @Nested
    @DisplayName("given an image file name")
    inner class GivenAnImageFileName {
        @Nested
        @DisplayName("when get its media type via image media type service")
        inner class WhenGetItsMediaTypeViaImageMediaTypeService {

            @InjectMocks
            private lateinit var imageMediaTypeService: ImageMediaTypeService

            @ParameterizedTest(name = "when image name is {0} then should return media type {1}")
            @CsvSource(
                "image.jpg, image/jpeg",
                "image.jpeg, image/jpeg",
                "xxx.png, image/png",
                "xxx.gif, image/gif",
                "unknown.bmp, multipart/form-data"
            )
            fun `then should return proper media type`(imageName: String, mediaType: String) {
                val result = imageMediaTypeService.getMediaType(imageName)

                assertThat(result).isEqualTo(MediaType.parseMediaType(mediaType))
            }
        }
    }
}