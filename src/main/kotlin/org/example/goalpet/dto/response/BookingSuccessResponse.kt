package org.example.goalpet.dto.response

import java.time.OffsetDateTime

data class BookingSuccessResponse(
    val username: String,
    val hotelName: String,
    val roomNumber: Long,
    val checkInDate: OffsetDateTime
)