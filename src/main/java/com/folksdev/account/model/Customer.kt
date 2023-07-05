package com.folksdev.account.model

import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*


/***
 * Kotlinde nesneyi olustururken sadece constructor kullanabiliyoruz. set get yok.
 * val kullandigimiz icin bu sekildedir.
 */
@Entity
data class Customer(

        /***
         * ? koydugum variable'lara default degeri kendim atamam gerek. Eger atamayacaksam da constructor yazmam gerek
         * Aksi halde constructor bulamadigina dair hata firlatiyor.
         */
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val id: String? = "",

        val name:String? = "",
        val surname:String? = "",

        /***
         * Ben customer'i cektigimde customer'a ait accountlari da cekebilmek istiyorum.
         * O yuzden one to many kullandik.
         */
        @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
        val account: Set<Account>? = setOf()
){
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Customer

                if (id != other.id) return false
                if (name != other.name) return false
                if (surname != other.surname) return false
                return account == other.account
        }

        override fun hashCode(): Int {
                var result = id?.hashCode() ?: 0
                result = 31 * result + (name?.hashCode() ?: 0)
                result = 31 * result + (surname?.hashCode() ?: 0)
                return result
        }
}
