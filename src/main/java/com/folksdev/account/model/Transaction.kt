package com.folksdev.account.model

import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Transaction(


        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val id: String?,
        val transactionType: TransactionType? = TransactionType.INITIAL, //default INITIAL olacak
        val amount: BigDecimal?, //double yerine BigDecimal kullanilmali
        val transactionDate: LocalDateTime,

        //Transaction dan account bilgisine erisebilmek icin
        @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = [CascadeType.ALL])
        @JoinColumn(name = "account_id", nullable = false)
        val account: Account

)

enum class TransactionType{
    INITIAL, TRANSFER
}
