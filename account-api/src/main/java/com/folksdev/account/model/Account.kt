package com.folksdev.account.model

import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity //Sinifin veritabanina karsilik gelen bir tablosu olmasi icin kullanilir
data class Account(

        /***
         * //Her account icin bir id olusturulmasini istiyorum.
         * Ancak bu ID'nin 3-5 gibi tahmin edilebilir olmasini istemiyorum.
         * Bu sebeple (generator = "UUID") kullaniyorum. (timestamp ile hashcode uretir. 32 harf ve rakam)
         */
        /***
         * val degerininin sonundaki ?, onun bos olabilecegi anlamina gelir
         */
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val id: String? = "",
        val balance: BigDecimal? = BigDecimal.ZERO,
        val creationDate: LocalDateTime?,


        /***
         * Customer - Account arasinda many to one iliskisi vardi. Yani bir musterinin, birden
         * fazla hesabi olabilir. Bu da demek oluyor ki benim account class'imin icinde
         * customer'i refere edecek bir adet customer nesne olmali.
         */
        /***
         * LAZY kullanim aciklamasi...
         * Ben account'u cagirdigimda customer gelsin istemiyorum. Boyle olursa eger,
         * ben accountu cagirdigimda customer gelecek, customer tekrardan account'u donecek ve
         * gereksiz bir loop request olacak. LAZY bunun onune gecer. Ben account.getCustomer
         * diyene kadar Customer nesnesinin degeri initialize edilmiyor. Ben cagirdigimda hybernate
         * araya giriyor, select sorgusunu yapiyor ve customer nesnesinin icini dolduruyor.
         * Bunun tam tersi EAGLE'dir.
         */
        /***
         * CascadeType.ALL aciklamasi...
         * Bu iliskiye ait herhangi bir entity'de yapilacak herhangi bir islemde (insert, delete update)
         * eger account'a ait bir customer nesnesi guncellendiyse, git customer tablosunu da guncelle demektir.
         * Benim duzenimde, account ekleniyorsa customer eklensin. customer siliniyorsa accountlari da silinsin.
         */
        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "customer_id", nullable = false)
        val customer: Customer?,

        /***
         * Account - Transaction arasinda one to many iliskisi var. Yani bir accountun,
         * birden fazla transaction'u (islemi) olabilir. Bu sebeple bu islemleri tutmak icin
         * Transaction listesi tutacagiz
         */
        /***
         * Bu account modelinde many to one kullandigimiz icin, hashcode functionunu override etmemiz gerek.
         * Cunku, ben veritabanindan veriyi cekerken, hybernate buradaki hashcode degerinin karsilastirmasini yapiyor
         * Eger ben customer ve transaction eklersem hashcode'a, bu sefer customer tablosundaki verileri de cekiyor,
         * Onu cekerken, account bilgilerini de cekiyor. Bu sebeple hashcode'u degistirmemiz gerek.
         */
        /***
         * mappedBy aciklamasi...
         * Transaction degeri, transaction modelinin icersinde account'a karsilik gelen nesnenin ismi
         * ikisini baglamis oldum. accountVariable deseydim, transaction class'indaki account nesnesinin ismini de
         * degistirmem gerekiyordu.
         */
        @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
        val transaction: Set<Transaction> = HashSet()

){
        constructor(customer: Customer, balance: BigDecimal, creationDate: LocalDateTime) : this(
                "",
                customer = customer,
                balance = balance,
                creationDate = creationDate
        )

        /***
         * OneToMany, ManyToOne iliskisi kurdugum icin, hashcode'umuzu kendimiz uretmeliyiz.
         * Aksi hashcode uzerinden karsilastirma yapildigi icin, halde benim nesnem loop'a giriyor
         * a.equals(b) function'unu override ettim.
         */
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Account

                if (id != other.id) return false
                if (balance != other.balance) return false
                if (creationDate != other.creationDate) return false
                if (customer != other.customer) return false

                return true
        }

        /***
         * Normalde burada generate ettigimde customer ve transaction da vardi,
         * Bunlari sildim. Cunku benim nesnemi sadece id, balance, creationDate ve customer
         * ile hashlemek istiyorum.
         */
        override fun hashCode(): Int {
                var result = id?.hashCode() ?: 0
                result = 31 * result + (balance?.hashCode() ?: 0)
                result = 31 * result + (creationDate?.hashCode() ?: 0)
                result = 31 * result + (customer?.hashCode() ?: 0)
                return result
        }
}
