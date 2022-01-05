package cn.devecor.upimage

import cn.devecor.upimage.model.MarkdownImg
import cn.devecor.upimage.util.TimeStampSupplier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.function.Supplier

@Service
class ImageStorageService(
    @Value("\${upimage.host}")
    private val host: String,
    private val imgRepository: ImageRepository,
    private val timeStampSupplier: TimeStampSupplier,
    private val imageIdSupplier: Supplier<String>
) {
    private val imageFolderName = "/image"

    @Deprecated(message = "2021/1/1")
    fun handleImage(file: MultipartFile): String {
        val path = "/${timeStampSupplier.get()}/${file.originalFilename}"
        imgRepository.save(file, path)
        return MarkdownImg(file.originalFilename!!, "$host$path").link
    }

    fun getImage(subPath: String) = imgRepository.get(imageFolderName + subPath)

    fun saveImage(image: MultipartFile): String {
        val path = "$imageFolderName/${imageIdSupplier.get()}/${image.originalFilename}"
        return if (imgRepository.save(image, path)) {
            host + path
        } else {
            ""
        }
    }
}