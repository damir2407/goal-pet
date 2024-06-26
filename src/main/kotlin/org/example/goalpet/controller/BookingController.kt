package org.example.goalpet.controller

import org.example.goalpet.dto.request.BookingRequest
import org.example.goalpet.dto.response.BookingSuccessResponse
import org.example.goalpet.service.BookingService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/bookings")
class BookingController(
    private val bookingService: BookingService
) {

    @PostMapping
    fun bookRoom(
        @RequestBody request: BookingRequest
    ): BookingSuccessResponse {
        return bookingService.bookRoom(request)
    }
}