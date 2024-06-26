package org.example.goalpet.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class VisitorCreateRequest(
    @field:NotBlank(message = "Поле username не может быть пустым!")
    val username: String,
    @field:Email(message = "Поле email должно представлять существующий адрес электронной почты!")
    val email: String
)