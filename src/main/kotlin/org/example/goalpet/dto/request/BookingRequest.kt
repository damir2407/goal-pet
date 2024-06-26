package org.example.goalpet.dto.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class BookingRequest(
    @field:NotBlank(message = "Поле name не может быть пустым!")
    val username: String,
    @field:Min(value = 1, message = "Значение поля roomId не может быть пустым!")
    val roomId: Long
)