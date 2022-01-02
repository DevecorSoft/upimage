package cn.devecor.upimage.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*
import java.util.function.Supplier

@Configuration
class ImageStorageConfig {

    @Bean
    @Qualifier("imageIdSupplier")
    fun imageIdSupplier(): Supplier<String> = Supplier {
        UUID.randomUUID().toString()
    }

    @Bean
    @Qualifier("upimageHomeSupplier")
    fun upimageHomeSupplier(@Value("\${upimage.home}") upimageHome: String): Supplier<String> = Supplier {
        if (upimageHome.startsWith("~")) "${System.getenv("HOME")}${upimageHome.substring(1)}"
        else upimageHome
    }
}