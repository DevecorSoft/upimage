package cn.devecor.upimage

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.web.multipart.MultipartFile
import java.io.File

@ExtendWith(MockitoExtension::class)
internal class ImageRepositoryTest {
    private val homePath = ""
    private val userHome = ""
    private val testHome = "unittest"
    private val imageRepository = ImageRepository(homePath, userHome)

    @Mock
    private lateinit var multipartFile: MultipartFile

    @Test
    fun `should save file into disk`() {
        val subPath = "/sub_path"
        imageRepository.save(multipartFile, subPath)
        verify(multipartFile).transferTo(File("${System.getProperty("user.home")}$homePath$subPath/${multipartFile.originalFilename}"))
    }

    @Nested
    @DisplayName("query file by path")
    inner class QueryFileByPath {

        @Nested
        @DisplayName("when image file is not existed")
        inner class FileNotExisted {
            @Test
            fun `should get null`() {
                val file = imageRepository.get("not existed")

                assertThat(file).isNull()
            }
        }

        @Nested
        @DisplayName("when image file is existed")
        inner class FileExisted {

            @Test
            fun `should get file`() {
                val file = imageRepository.get("$testHome/text.txt")

                assertThat(file?.exists()).isTrue
                assertThat(file).isFile
            }

            @BeforeEach
            fun setup() {
                createFile()
            }

            private fun createFile(): File {
                File(testHome).mkdir()
                val expectedFile = File("$testHome/text.txt")
                expectedFile.writeText("")
                return expectedFile
            }
        }
    }

    @BeforeEach
    @AfterEach
    fun cleanTestFolder() {
        File(testHome).deleteRecursively()
    }
}