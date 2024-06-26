package org.example.goalpet.service

import org.example.goalpet.config.TASK_EXECUTOR_SERVICE
import org.example.goalpet.domain.Hotel
import org.example.goalpet.repository.HotelRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class HotelService(
    private val hotelRepository: HotelRepository
) {

    @Async(TASK_EXECUTOR_SERVICE)
    fun findAllHotels(page: Int, size: Int): CompletableFuture<Page<Hotel>> {
        val pageable = PageRequest.of(page, size)
        return hotelRepository.findAllBy(pageable)
    }
}