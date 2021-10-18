package cn.devecor.upimage

import cn.devecor.upimage.model.MarkdownImg
import cn.devecor.upimage.util.TimeStampSupplier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ImageStorageService(
    @Value("\${upimage.host}")
    private val host: String,
    private val imgRepository: ImageRepository,
    private val timeStampSupplier: TimeStampSupplier
) {

    fun handleImage(file: MultipartFile): String {
        val path = "/${timeStampSupplier.get()}"
        imgRepository.save(file, path)
        return MarkdownImg(file.originalFilename!!, "$host$path/${file.originalFilename}").link
    }

    fun getImage(subPath: String) = imgRepository.get(subPath)!!
}