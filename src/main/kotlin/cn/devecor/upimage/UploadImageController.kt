package cn.devecor.upimage

import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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

    @CrossOrigin
    @GetMapping(Endpoints.GET_IMAGE)
    fun getImage(@PathVariable path: String, @PathVariable filename: String): ResponseEntity<Resource> {

        val file = imageStorageService.getImage("/$path/$filename") ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(UrlResource(file.toURI()))
    }
}
