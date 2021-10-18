package cn.devecor.upimage

import org.junit.jupiter.api.Test
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
    private val imageRepository = ImageRepository(homePath, userHome)

    @Mock
    private lateinit var multipartFile: MultipartFile

    @Test
    fun `should save file into disk`() {
        val subPath = "/sub_path"
        imageRepository.save(multipartFile, subPath)
        verify(multipartFile).transferTo(File("${System.getProperty("user.home")}$homePath$subPath/${multipartFile.originalFilename}"))
    }
}