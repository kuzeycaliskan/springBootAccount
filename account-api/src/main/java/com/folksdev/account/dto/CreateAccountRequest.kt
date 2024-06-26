package com.folksdev.account.dto

import org.springframework.lang.Nullable
import java.math.BigDecimal
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank


/***
 * Field annotationlarinin gecerli olmasi icin Request'in yapildigi controller'da @Valid assertion'unu
 * kullanmam gerekir. Account Controller class'ini kontrol edebilirsin.
 */
class CreateAccountRequest(
        //String'in bos olmamasi icin kullandik. Sadece Kotlin'de bu sekilde yazabilirim
        @field:NotBlank(message = "Istedigim mesaji yazabilirim, veya bos birakabilirim. Mesaj dili OS dilinden alinir")
        val customerId: String,
)
