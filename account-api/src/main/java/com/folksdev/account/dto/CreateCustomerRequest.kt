package com.folksdev.account.dto

import javax.validation.constraints.NotBlank

class CreateCustomerRequest (
    @field:NotBlank(message = "Customer name cannot be null")
    val customerName: String,
    @field:NotBlank(message = "Customer surname cannot be null")
    val customerSurname: String
)