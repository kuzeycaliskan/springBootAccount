package com.folksdev.account.dto

/***
 * CustomerDto icinde customer'in accountlarinin bilgilerinin olmasini istiyorum
 * AccountDto icinde customer bilgilerinin olmasini istiyorum
 * Bu sebeple bu class'i kurdum
 */
data class AccountCustomerDto(
        val id: String,
        val name: String,
        val surname: String,
){

}
