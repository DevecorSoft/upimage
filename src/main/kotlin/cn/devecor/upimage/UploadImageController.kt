package cn.devecor.upimage

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class UploadImageController(
    private val imageStorageService: ImageStorageService
) {

    @CrossOrigin
    @PostMapping(Endpoints.UPLOAD_IMAGE)
    fun handleImageUpload(file: MultipartFile): ResponseEntity<String> {
        return ResponseEntity.ok(imageStorageService.handleImage(file))
    }
}
