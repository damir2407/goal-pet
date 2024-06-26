package org.example.goalpet.repository

import org.example.goalpet.config.TASK_EXECUTOR_REPOSITORY
import org.example.goalpet.domain.Hotel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Repository
import java.util.concurrent.CompletableFuture

@Repository
interface HotelRepository : JpaRepository<Hotel, Long> {

    @Async(TASK_EXECUTOR_REPOSITORY)
    fun findAllBy(pageable: Pageable): CompletableFuture<Page<Hotel>>
}