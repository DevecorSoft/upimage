package cn.devecor.upimage

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException

@Repository
class ImageRepository(
    @Value("\${upimage.home}")
    private val homePath: String,
    @Value("\${user.home}")
    private val userHome: String,
) {

    fun save(file: MultipartFile, path: String): Boolean {
        val dest = File("$userHome$homePath$path/${file.originalFilename}")
        if (!dest.exists()) {
            dest.mkdirs()
        }
        return try {
            file.transferTo(dest)
            true
        } catch (e: IOException) {
            false
        } catch (e: IllegalStateException) {
            false
        }
    }

    fun get(filepath: String): File? {
        val file = File("$userHome$homePath$filepath")
        return if (file.exists()) file else null
    }
}