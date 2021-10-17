package cn.devecor.upimage.util

import org.springframework.stereotype.Service
import java.util.function.Supplier

@Service
class TimeStampSupplier : Supplier<Long> {
    override fun get() = System.currentTimeMillis()
}