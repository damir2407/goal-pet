package org.example.goalpet.controller

import org.example.goalpet.config.TASK_EXECUTOR_CONTROLLER
import org.example.goalpet.domain.Room
import org.example.goalpet.service.RoomService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("api/v1/rooms")
class RoomController(
    val roomService: RoomService
) {

    @Async(TASK_EXECUTOR_CONTROLLER)
    @GetMapping
    fun getRoomsByHotel(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "5", required = false) size: Int,
        @RequestParam(defaultValue = "", required = false) hotelName: String,
    ): CompletableFuture<ResponseEntity<Page<Room>>> {
        return roomService
            .findAllRoomsByHotel(page, size, hotelName)
            .thenApply { ResponseEntity.ok(it) }
            .exceptionally { ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build() }
    }
}