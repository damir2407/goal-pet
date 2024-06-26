package org.example.goalpet.controller

import org.example.goalpet.config.TASK_EXECUTOR_CONTROLLER
import org.example.goalpet.domain.Hotel
import org.example.goalpet.service.BookingService
import org.example.goalpet.service.BookingService.HotelBookingRequest
import org.example.goalpet.service.HotelService
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("api/v1/hotels")
class HotelController(
    private val bookingService: BookingService,
    private val hotelService: HotelService
) {

    @Async(TASK_EXECUTOR_CONTROLLER)
    @GetMapping
    fun getHotels(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int
    ): CompletableFuture<ResponseEntity<Page<Hotel>>> {
        return hotelService
            .findAllHotels(page, size)
            .thenApply { ResponseEntity.ok(it) }
    }

    @GetMapping("booking-requests")
    fun getHotelsBookingRequests(): List<HotelBookingRequest> {
        return bookingService.getHotelRequests()
    }
}