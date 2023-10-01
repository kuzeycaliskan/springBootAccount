package com.folksdev.account.dto

import java.math.BigDecimal
import java.time.LocalDateTime

/***
 * Zaten Model olusturduk, dto'yu niye olusturuyoruz. Duplicated code olmuyor mu diye dusunebiliriz.
 * Ancak Ben dto icine ornegin transaction'lari koymayabilirim. Veya, model icine koymayip dto'ya
 * koyacagim seyler olabilir.
 * DTO'lar bizim API response'larimizda daha esnek olmamizi ve daha kontrollu olmamizi saglar.
 * Kesinlikle kullanmaliyiz.
 */
data class AccountDto(
        val id: String?,
        val balance: BigDecimal? = BigDecimal.ZERO,
        val creationDate: LocalDateTime,
        val customer: AccountCustomerDto?,
        val transactions: Set<TransactionDto>?
)