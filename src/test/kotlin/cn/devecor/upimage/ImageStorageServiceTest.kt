package cn.devecor.upimage

import cn.devecor.upimage.util.TimeStampSupplier
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.Calendar
import java.util.function.Supplier

@ExtendWith(MockitoExtension::class)
internal class ImageStorageServiceTest {

    @Mock
    private lateinit var multipartFile: MultipartFile

    @Mock
    private lateinit var timeStampSupplier: TimeStampSupplier

    @Mock
    private lateinit var imageRepository: ImageRepository

    @Mock
    private lateinit var imageIdSupplier: Supplier<String>

    private val host = "http://fake.devecor.cn"
    private val testHome = "unittest"

    private lateinit var imageStorageService: ImageStorageService

    @BeforeEach
    fun setup() {
        imageStorageService = ImageStorageService(host, imageRepository, timeStampSupplier, imageIdSupplier)
    }

    @Test
    fun `should return a markdown image link`() {

        val filename = "avatar.jpg"
        val timestamp = Calendar.Builder().setDate(2020, 9, 22).build().timeInMillis

        `when`(multipartFile.originalFilename).thenReturn(filename)
        `when`(timeStampSupplier.get()).thenReturn(timestamp)

        assertThat(imageStorageService.handleImage(multipartFile))
            .isEqualTo("![$filename]($host/$timestamp/$filename)")
    }

    @Nested
    @DisplayName("get image")
    inner class GetImage {

        @Nested
        @DisplayName("when image file is not existed")
        inner class ImageNotExisted {
            @Test
            fun `should return a null`() {

                `when`(imageRepository.get("")).thenReturn(null)

                val file = imageStorageService.getImage("")

                assertThat(file).isNull()
            }
        }

        @Nested
        @DisplayName("when image file is existed")
        inner class ImageExisted {

            @Test
            fun `should return an image file`() {
                val expectedFile = createFile()

                `when`(imageRepository.get(expectedFile.path)).thenReturn(expectedFile)

                val file = imageStorageService.getImage(expectedFile.path)

                assertThat(file).isFile
                assertThat(file).isEqualTo(expectedFile)
            }
        }
    }

    @Nested
    @DisplayName("given multipart file")
    inner class GivenMultipartFile {

        @Nested
        @DisplayName("when successfully save it by image storage service")
        inner class WhenSaveItByImageStorageService {

            @ParameterizedTest(name = "save image with image id {0} and image name {1}")
            @CsvSource(
                "12345678, image.png",
                "31244556, picture.jpg"
            )
            fun `then return path to image it saved`(imageId: String, imageName: String) {
                `when`(imageIdSupplier.get()).thenReturn(imageId)
                `when`(multipartFile.originalFilename).thenReturn(imageName)
                `when`(imageRepository.save(multipartFile, "/image/$imageId/$imageName")).thenReturn(true)

                val path = imageStorageService.saveImage(multipartFile)

                assertThat(path).isEqualTo("/image/$imageId/$imageName")
                verify(imageRepository).save(multipartFile, path)
            }
        }

        @Nested
        @DisplayName("when save it fails")
        inner class WhenSaveItFails {

            @Test
            fun `should return empty string`() {
                `when`(imageIdSupplier.get()).thenReturn("12345678")
                `when`(multipartFile.originalFilename).thenReturn("image.jpg")
                `when`(imageRepository.save(multipartFile, "/image/12345678/image.jpg")).thenReturn(false)

                val path = imageStorageService.saveImage(multipartFile)

                assertThat(path).isEmpty()
            }
        }
    }

    private fun createFile(): File {
        File(testHome).mkdir()
        val expectedFile = File("$testHome/text.txt")
        expectedFile.writeText("")
        return expectedFile
    }

    @BeforeEach
    @AfterEach
    fun cleanTestFolder() {
        File(testHome).deleteRecursively()
    }
}