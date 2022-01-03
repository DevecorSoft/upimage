package cn.devecor.upimage

import org.springframework.http.MediaType
import org.springframework.stereotype.Service

@Service
class ImageMediaTypeService {

    fun getMediaType(imageName: String): MediaType {

        return when (imageName.split(".").last()) {
            "jpg" -> MediaType.IMAGE_JPEG
            "jpeg" -> MediaType.IMAGE_JPEG
            "png" -> MediaType.IMAGE_PNG
            "gif" -> MediaType.IMAGE_GIF
            else -> MediaType.MULTIPART_FORM_DATA
        }
    }
}