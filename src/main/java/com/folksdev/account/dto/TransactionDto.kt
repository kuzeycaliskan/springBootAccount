package com.folksdev.account.dto

import com.folksdev.account.model.TransactionType
import java.math.BigDecimal
import java.time.LocalDateTime

data class TransactionDto(
        val id: String?,
        val transactionType: TransactionType? = TransactionType.INITIAL, //default INITIAL olacak
        val amount: BigDecimal?, //double yerine BigDecimal kullanilmali
        val transactionDate: LocalDateTime,
)
