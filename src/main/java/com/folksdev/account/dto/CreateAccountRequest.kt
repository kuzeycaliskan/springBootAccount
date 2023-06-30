package com.folksdev.account.dto

import java.math.BigDecimal

class CreateAccountRequest(
        val customerId: String,
        val initialCredit: BigDecimal
)
