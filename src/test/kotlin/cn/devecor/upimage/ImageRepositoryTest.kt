package cn.devecor.upimage

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException

@ExtendWith(MockitoExtension::class)
internal class ImageRepositoryTest {
    private val testHome = "unittest"

    private val imageRepository = ImageRepository { testHome }

    @Nested
    @DisplayName("given a multipartfile and a filepath")
    inner class GivenAMultipartfileAndAFilepath {

        @Mock
        private lateinit var multipartFile: MultipartFile

        @Nested
        @DisplayName("when save image without failure")
        inner class WhenSaveImageWithDedicatePath {
            @ParameterizedTest(name = "with dedicated path {0}")
            @CsvSource(
                "/image/12345678/image.png",
                "/image/92374803929/picture.jpg"
            )
            fun `then should call multipartFile transferTo`(path: String) {
                val result = imageRepository.save(multipartFile, path)

                verify(multipartFile).transferTo(File(testHome + path))
                assertThat(result).isTrue
            }
        }

        @BeforeEach
        fun `set up`() {
            File(testHome).setWritable(true)
        }

        @Nested
        @DisplayName("when save image fails")
        inner class WhenSaveImageFails {
            @Test
            fun `then should return just false without permission`() {
                val dir = File(testHome)
                dir.mkdir()
                dir.setWritable(false, true)

                val result = imageRepository.save(multipartFile, "/xxx.png")

                assertThat(result).isFalse
            }

            @ParameterizedTest(name = "with {0}")
            @ValueSource(classes = [
                IOException::class, IllegalStateException::class]
            )
            fun `then should return just false`(throwable: Class<out Throwable>) {
                `when`(multipartFile.transferTo(File("${testHome}/image/1234567/xxx.png"))).thenThrow(throwable)

                val result = imageRepository.save(multipartFile, "/image/1234567/xxx.png")

                assertThat(result).isFalse
            }
        }
    }

    @Nested
    @DisplayName("given an image path")
    inner class GivenAnExistedImageWithPath {

        @Nested
        @DisplayName("when query file by path but it's not existed")
        inner class QueryFileByPath {

            @Test
            fun `then should get null`() {
                val file = imageRepository.get("not existed")

                assertThat(file).isNull()
            }
        }

        @Nested
        @DisplayName("when image file with the path is existed")
        inner class FileExisted {

            @Test
            fun `then should get the image file`() {
                val file = imageRepository.get("/text.txt")

                assertThat(file)!!.exists()
                assertThat(file).isFile
            }

            @BeforeEach
            fun setup() {
                createFile()
            }

            private fun createFile() {
                File(testHome).mkdir()
                val expectedFile = File("$testHome/text.txt")
                expectedFile.writeText("")
            }
        }
    }

    @BeforeEach
    @AfterEach
    fun cleanTestFolder() {
        File(testHome).setWritable(true)
        File(testHome).deleteRecursively()
    }
}