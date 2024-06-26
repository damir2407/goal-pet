package org.example.goalpet.dto.response

import java.time.OffsetDateTime

data class ErrorResponse(
    val errorMessage: String,
    val timestamp: OffsetDateTime
)

