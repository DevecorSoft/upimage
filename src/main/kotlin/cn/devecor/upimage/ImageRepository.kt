package cn.devecor.upimage

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Repository
class ImageRepository(
    @Value("\${upimage.home}")
    private val homePath: String,
    @Value("\${user.home}")
    private val userHome: String,
) {

    fun save(file: MultipartFile, path: String) {
        val dest = File("$userHome$homePath$path/${file.originalFilename}")
        if (!dest.exists()) {
            dest.mkdirs()
        }
        file.transferTo(dest)
    }

    fun get(filepath: String): File? {
        return null
    }
}