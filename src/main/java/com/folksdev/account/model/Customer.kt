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

        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val id: String?,

        val name:String?,
        val surname:String?,

        /***
         * Ben customer'i cektigimde customer'a ait accountlari da cekebilmek istiyorum.
         * O yuzden one to many kullandik.
         */
        @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
        val account: Set<Account>?
)
