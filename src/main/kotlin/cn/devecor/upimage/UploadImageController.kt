package cn.devecor.upimage

import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.FileInputStream

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

        return ResponseEntity.ok()
            .contentLength(file.length())
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(InputStreamResource(FileInputStream(file)))
    }

    @CrossOrigin
    @PostMapping("/image")
    fun postImage(file: MultipartFile): ResponseEntity<String> {
        val url = imageStorageService.saveImage(file)
        return if (url.isNotEmpty()) {
            ResponseEntity.ok(url)
        } else {
            ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build()
        }
    }
}
