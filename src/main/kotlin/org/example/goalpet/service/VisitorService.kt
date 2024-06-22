package org.example.goalpet.service

import mu.KLogging
import org.example.goalpet.config.TASK_EXECUTOR_SERVICE
import org.example.goalpet.domain.Visitor
import org.example.goalpet.repository.VisitorRepository
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture


@Service
class VisitorService(
    private val visitorRepository: VisitorRepository
) {

    @Async(TASK_EXECUTOR_SERVICE)
    fun findSingleById(id: Long): CompletableFuture<Visitor?> {
        return visitorRepository
            .findSingleById(id)
    }

    companion object : KLogging()
}