package cn.devecor.upimage.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*
import java.util.function.Supplier

@Configuration
class ImageStorageConfig {

    @Bean
    fun imageIdSupplier(): Supplier<String> = Supplier {
        UUID.randomUUID().toString()
    }
}