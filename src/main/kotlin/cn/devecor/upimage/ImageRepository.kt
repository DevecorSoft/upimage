package cn.devecor.upimage

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.util.function.Supplier

@Repository
class ImageRepository(
    @Qualifier("upimageHomeSupplier")
    private val upimageHome: Supplier<String>,
) {

    fun save(file: MultipartFile, path: String): Boolean {
        val dest = File("${upimageHome.get()}$path")
        if (!dest.mkdirs()) {
            return false
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
        val file = File("${upimageHome.get()}$filepath")
        return if (file.exists()) file else null
    }
}