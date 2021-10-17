package cn.devecor.upimage

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Repository
class ImageRepository(
    @Value("\${upimage.home}")
    private val homePath: String
) {

    fun save(file: MultipartFile, path: String) {
        val userHome = System.getProperty("user.home")
        val dest = File("$userHome$homePath$path/${file.originalFilename}")
        if (!dest.exists()) {
            dest.mkdirs()
        }
        file.transferTo(dest)
    }
}