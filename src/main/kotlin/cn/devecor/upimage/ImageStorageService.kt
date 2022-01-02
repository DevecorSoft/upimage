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

    @Deprecated(message = "2021/1/1")
    fun handleImage(file: MultipartFile): String {
        val path = "/${timeStampSupplier.get()}"
        imgRepository.save(file, path)
        return MarkdownImg(file.originalFilename!!, "$host$path/${file.originalFilename}").link
    }

    fun getImage(subPath: String) = imgRepository.get(subPath)

    fun saveImage(image: MultipartFile): String {
        val path =  "/image/${imageIdSupplier.get()}/${image.originalFilename}"
        return if (imgRepository.save(image, path)) {
            path
        } else {
            ""
        }
    }
}