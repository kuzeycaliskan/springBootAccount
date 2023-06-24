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

){
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Transaction

                if (id != other.id) return false
                if (transactionType != other.transactionType) return false
                if (amount != other.amount) return false
                if (transactionDate != other.transactionDate) return false
                return account == other.account
        }

        override fun hashCode(): Int {
                var result = id?.hashCode() ?: 0
                result = 31 * result + (transactionType?.hashCode() ?: 0)
                result = 31 * result + (amount?.hashCode() ?: 0)
                result = 31 * result + transactionDate.hashCode()
                return result
        }
}

enum class TransactionType{
    INITIAL, TRANSFER
}
