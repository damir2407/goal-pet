package org.example.goalpet.service

import org.example.goalpet.config.TASK_EXECUTOR_SERVICE
import org.example.goalpet.domain.Room
import org.example.goalpet.repository.RoomRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class RoomService(
    private val roomRepository: RoomRepository
) {

    @Async(TASK_EXECUTOR_SERVICE)
    fun findAllRoomsByHotel(page: Int, size: Int, hotelName: String): CompletableFuture<Page<Room>> {
        val pageable = PageRequest.of(page, size)
        return roomRepository.findAllByHotelNameContains(hotelName, pageable)
    }
}