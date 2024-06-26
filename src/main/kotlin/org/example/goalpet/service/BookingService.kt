package org.example.goalpet.service

import com.google.common.cache.CacheBuilder
import org.example.goalpet.domain.Booking
import org.example.goalpet.domain.Hotel
import org.example.goalpet.dto.request.BookingRequest
import org.example.goalpet.dto.response.BookingSuccessResponse
import org.example.goalpet.repository.BookingRepository
import org.example.goalpet.repository.RoomRepository
import org.example.goalpet.repository.VisitorRepository
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.OffsetDateTime
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock

@Service
class BookingService(
    private val bookingRepository: BookingRepository,
    private val roomRepository: RoomRepository,
    private val visitorRepository: VisitorRepository
) {

    private val locks: ConcurrentMap<Long, ReentrantLock> = CacheBuilder.newBuilder()
        .concurrencyLevel(4)
        .expireAfterWrite(Duration.ofSeconds(2))
        .build<Long, ReentrantLock>().asMap()

    private val hotelRequests: ConcurrentMap<Hotel, AtomicInteger> = CacheBuilder.newBuilder()
        .concurrencyLevel(4)
        .expireAfterWrite(Duration.ofMinutes(15))
        .build<Hotel, AtomicInteger>().asMap()

    fun bookRoom(request: BookingRequest): BookingSuccessResponse {
        val visitor = visitorRepository.findByUsername(request.username) ?: error("No username found")
        var room = roomRepository.findById(request.roomId).orElseThrow()//TODO

        if (room.availibility) {
            val lock = locks.computeIfAbsent(request.roomId) { ReentrantLock() }
            lock.lock()
            try {
                room = roomRepository.findById(request.roomId).orElseThrow()//TODO
                if (room.availibility) {
                    val booking = bookingRepository.save(
                        Booking(
                            visitor,
                            room,
                            OffsetDateTime.now()
                        )
                    )
                    roomRepository.save(room.apply { availibility = false })

                    val hotelRequestsCounter = hotelRequests.computeIfAbsent(room.hotel) { AtomicInteger() }
                    hotelRequestsCounter.incrementAndGet()

                    return BookingSuccessResponse(
                        visitor.username,
                        room.hotel.name,
                        room.id!!,
                        booking.checkInDate
                    )
                }
            } finally {
                lock.unlock()
            }
        }

        error("Номер занят!")
    }

    fun getHotelRequests(): List<HotelBookingRequest> {
        return hotelRequests.map { (k, v) -> HotelBookingRequest(k, v.get()) }
    }

    data class HotelBookingRequest(
        val hotel: Hotel,
        val count: Int
    )
}