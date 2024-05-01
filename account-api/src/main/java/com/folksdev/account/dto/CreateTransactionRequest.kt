package com.folksdev.account.dto

import org.springframework.lang.Nullable
import java.math.BigDecimal
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank


/***
 * Field annotationlarinin gecerli olmasi icin Request'in yapildigi controller'da @Valid assertion'unu
 * kullanmam gerekir. Account Controller class'ini kontrol edebilirsin.
 */
class CreateTransactionRequest(
        @field:NotBlank(message = "Account cannot be null")
        val accountId: String,
        @field:Min(0, message = "Initial Credit value must not be negative value") //initialCredit 0 dan kucuk olmasin diye bunu kullandik.
        val initialCredit: BigDecimal
)
