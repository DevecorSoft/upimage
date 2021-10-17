package cn.devecor.upimage

import cn.devecor.upimage.util.TimeStampSupplier
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.web.multipart.MultipartFile
import java.util.Calendar

@ExtendWith(MockitoExtension::class)
internal class ImageStorageServiceTest {

    @Mock
    private lateinit var multipartFile: MultipartFile

    @Mock
    private lateinit var timeStampSupplier: TimeStampSupplier

    @Mock
    private lateinit var imageRepository: ImageRepository

    private val host = "http://fake.devecor.cn"

    @Test
    fun `should return a markdown image link`() {
        val imageStorageService = ImageStorageService(host, imageRepository, timeStampSupplier)

        val filename = "avatar.jpg"
        val timestamp = Calendar.Builder().setDate(2020, 9, 22).build().timeInMillis

        `when`(multipartFile.originalFilename).thenReturn(filename)
        `when`(timeStampSupplier.get()).thenReturn(timestamp)

        assertThat(imageStorageService.handleImage(multipartFile))
            .isEqualTo("![$filename]($host/$timestamp/$filename)")
    }
}