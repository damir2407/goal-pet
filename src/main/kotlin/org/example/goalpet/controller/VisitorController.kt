package org.example.goalpet.controller

import org.example.goalpet.config.TASK_EXECUTOR_CONTROLLER
import org.example.goalpet.domain.Visitor
import org.example.goalpet.service.VisitorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("api/v1/visitors")
class VisitorController(
    private val visitorService: VisitorService
) {

    @Async(TASK_EXECUTOR_CONTROLLER)
    @GetMapping("/{visitorId}")
    fun getVisitor(@PathVariable visitorId: Long): CompletableFuture<ResponseEntity<Visitor?>> {
        return visitorService
            .findSingleById(visitorId)
            .thenApply { ResponseEntity.ok(it) }
            .exceptionally { ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build() }
    }
}