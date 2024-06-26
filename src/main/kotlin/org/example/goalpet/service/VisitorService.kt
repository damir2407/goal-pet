package org.example.goalpet.service

import com.google.common.cache.CacheBuilder
import mu.KLogging
import org.example.goalpet.config.TASK_EXECUTOR_SERVICE
import org.example.goalpet.domain.Visitor
import org.example.goalpet.dto.request.VisitorCreateRequest
import org.example.goalpet.exception.VisitorException
import org.example.goalpet.repository.VisitorRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.locks.ReentrantLock


@Service
class VisitorService(
    private val visitorRepository: VisitorRepository
) {

    private val locks: ConcurrentMap<String, ReentrantLock> = CacheBuilder.newBuilder()
        .concurrencyLevel(4)
        .expireAfterWrite(Duration.ofSeconds(2))
        .build<String, ReentrantLock>().asMap()

    @Async(TASK_EXECUTOR_SERVICE)
    fun findAllVisitors(page: Int, size: Int): CompletableFuture<Page<Visitor>> {
        val pageable = PageRequest.of(page, size)
        return visitorRepository.findAllBy(pageable)
    }

    fun createVisitor(request: VisitorCreateRequest): Visitor {
        var visitor = visitorRepository.findByUsername(request.username)
        if (visitor == null) {
            val lock = locks.computeIfAbsent(request.username) { ReentrantLock() }
            lock.lock()
            try {
                visitor = visitorRepository.findByUsername(request.username)
                if (visitor == null) {
                    visitor = visitorRepository.save(Visitor(request.username, request.email))
                }
            } finally {
                lock.unlock()
            }
        }
        return visitor ?: throw VisitorException("Пользователь ${request.username} уже зарегистрирован!")
    }

    companion object : KLogging()
}